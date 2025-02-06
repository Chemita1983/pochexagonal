package com.orange.shoppingcart.infraestructure;

import com.orange.openapi.api.model.Signature;
import com.orange.openapiosp.boot.errorhandler.exception.OpenApiBadRequestException;
import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import com.orange.shoppingcart.signature.domain.model.SignatureTypes;
import com.orange.shoppingcart.signature.domain.ports.input.SignatureUseCasePort;
import com.orange.shoppingcart.signature.infrastructure.api.SignatureController;
import com.orange.shoppingcart.signature.infrastructure.mapper.SignatureMapper;
import com.orange.shoppingcart.signature.infrastructure.mapper.SignatureMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SignatureControllerTest {

    @Mock
    SignatureUseCasePort signatureUseCasePort;

    @Spy
    SignatureMapper signatureMapper = new SignatureMapperImpl();

    @InjectMocks
    SignatureController signatureController;

    private static Stream<Arguments> withValidValues() {
        return Stream.of(
                Arguments.of("NIF", List.of("alta"), "España", "autonomo"),
                Arguments.of("NIF", List.of("alta"), "España", "empresa"),
                Arguments.of("NIF", List.of("TODOS"), "España", "empresa"),
                Arguments.of("TODOS", List.of("TODOS"), "Francia", "TODOS")
        );
    }

    private static Stream<Arguments> withInvalidValues() {
        return Stream.of(
                Arguments.of("NIF", List.of("alta"), "", "autonomo"),
                Arguments.of(null, List.of("alta"), "España", "empresa"),
                Arguments.of("", List.of("alta"), "España", "autonomo"),
                Arguments.of("CARNET DE CONDUCIR", List.of("alta"), "España", "empresa"),
                Arguments.of("NIF", Collections.emptyList(), "España", "empresa"),
                Arguments.of("TODOS", null, "Francia", "empresa"),
                Arguments.of("NIF", Collections.singletonList(null), "España", "empresa"),
                Arguments.of("NIF", List.of("alta"), "", "autonomo"),
                Arguments.of("NIF", List.of("alta"), null, "autonomo"),
                Arguments.of("TODOS", List.of("TODOS"), "España", "JUAN"),
                Arguments.of("TODOS", List.of("TODOS"), "España", null),
                Arguments.of("TODOS", List.of("TODOS"), "España", "")
        );
    }

    @ParameterizedTest
    @MethodSource("withValidValues")
    void getSignatureTypeAllowedGivenValidValuesThenReturnOK(final String documentType, final List<String> commercialAct, final String nationality, final String segment) {

        when(signatureUseCasePort.getSignatureTypes(any(SignatureDataInput.class))).thenReturn(new SignatureTypes(true, true));
        ResponseEntity<Signature> signatureTypeAllowed = signatureController.getSignatureTypeAllowed(documentType, commercialAct, nationality, segment);

        Assertions.assertNotNull(signatureTypeAllowed.getBody());
        Assertions.assertTrue(signatureTypeAllowed.getBody().getAllowedManualSignature());
        Assertions.assertTrue(signatureTypeAllowed.getBody().getAllowedDigitalSignature());
    }

    @ParameterizedTest
    @MethodSource("withInvalidValues")
    void getSignatureTypeAllowedGivenInvalidValuesThenReturnException(final String documentType, final List<String> commercialAct, final String nationality, final String segment) {
        assertThrows(OpenApiBadRequestException.class, () -> {
            signatureController.getSignatureTypeAllowed(documentType, commercialAct, nationality, segment);
        });
    }
}
