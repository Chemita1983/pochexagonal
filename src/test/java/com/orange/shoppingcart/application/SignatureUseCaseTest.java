package com.orange.shoppingcart.application;

import com.orange.openapiosp.boot.errorhandler.autoconfigure.ErrorHandlerAutoconfiguration;
import com.orange.openapiosp.boot.errorhandler.exception.OpenApiBadRequestException;
import com.orange.shoppingcart.signature.application.SignatureUseCase;
import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import com.orange.shoppingcart.signature.domain.model.SignatureTypes;
import com.orange.shoppingcart.signature.domain.ports.output.NationalityPort;
import com.orange.shoppingcart.signature.domain.ports.output.SignaturePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ErrorHandlerAutoconfiguration.class)
public class SignatureUseCaseTest {

    @Mock
    NationalityPort nationalityPort;

    @Mock
    SignaturePort signaturePort;

    @InjectMocks
    SignatureUseCase signatureUseCase;


    private static Stream<SignatureDataInput> withValidValues() {
        return Stream.of(
                new SignatureDataInput("NIF", List.of("alta"), "España", "autonomo"),
                new SignatureDataInput("NIF", List.of("alta"), "España", "empresa"),
                new SignatureDataInput("NIF", List.of("TODOS"), "España", "empresa"),
                new SignatureDataInput("TODOS", List.of("TODOS"), "Francia", "TODOS")
        );
    }

    private static Stream<SignatureDataInput> withInvalidNationality() {
        return Stream.of(
                new SignatureDataInput("NIF", List.of("alta"), "Leganes", "empresa"),
                new SignatureDataInput("NIF", List.of("alta"), "12345", "empresa")
        );
    }

    @ParameterizedTest
    @MethodSource("withValidValues")
    void getSignatureTypesWithValidValuesThenReturnOK(SignatureDataInput signatureDataInput) {

        when(nationalityPort.existNationality(signatureDataInput.nationality())).thenReturn(Boolean.TRUE);
        when(signaturePort.getSignatureTypes(signatureDataInput)).thenReturn(new SignatureTypes(true, true));

        SignatureTypes signatureTypes = signatureUseCase.getSignatureTypes(signatureDataInput);

        Assertions.assertTrue(signatureTypes.allowedManualSignature());
        Assertions.assertTrue(signatureTypes.allowedDigitalSignature());
    }

    @ParameterizedTest
    @MethodSource("withInvalidNationality")
    void getSignatureTypesWithInvalidNationalityThrowsBadRequestException(SignatureDataInput signatureDataInput) {
        assertThrows(OpenApiBadRequestException.class, () -> signatureUseCase.getSignatureTypes(signatureDataInput));
    }
}

