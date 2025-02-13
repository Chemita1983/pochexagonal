package com.orange.shoppingcart.application;

import com.orange.openapiosp.boot.errorhandler.autoconfigure.ErrorHandlerAutoconfiguration;
import com.orange.openapiosp.boot.errorhandler.exception.OpenApiBadRequestException;
import com.orange.shoppingcart.signature.application.SignatureUseCase;
import com.orange.shoppingcart.signature.application.mapper.SignatureUseCaseMapper;
import com.orange.shoppingcart.signature.application.mapper.SignatureUseCaseMapperImpl;
import com.orange.shoppingcart.signature.domain.model.Signature;
import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import com.orange.shoppingcart.signature.domain.model.SignatureTypes;
import com.orange.shoppingcart.signature.domain.ports.output.NationalityPort;
import com.orange.shoppingcart.signature.domain.ports.output.SignaturePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ErrorHandlerAutoconfiguration.class)
public class SignatureUseCaseTest {


    @Mock
    NationalityPort nationalityPort;

    @Mock
    SignaturePort signaturePort;

    @Spy
    SignatureUseCaseMapper signatureUseCaseMapper = new SignatureUseCaseMapperImpl();

    @InjectMocks
    SignatureUseCase signatureUseCase;


    private static Stream<Arguments> withValidValues() {
        return Stream.of(
                Arguments.of("NIF", List.of("alta"), "España", "autonomo"),
                Arguments.of("NIF", List.of("alta"), "España", "empresa"),
                Arguments.of("NIF", List.of("TODOS"), "España", "empresa"),
                Arguments.of("TODOS", List.of("TODOS"), "Francia", "TODOS"),
                Arguments.of("CIF", List.of("alta"), "España", "residencial"),
                Arguments.of("TARJETA DE RESIDENCIA", List.of("alta"), "España", "residencial"),
                Arguments.of("PASAPORTE", List.of("alta"), "España", "residencial"),
                Arguments.of("NIF", List.of("alta", "renove"), "España", "empresa"),
                Arguments.of("NIF", List.of("alta", "renove", "migracion"), "España", "empresa"),
                Arguments.of("NIF", List.of("alta", "renove", "migracion", "cambiotarifa"), "España", "empresa"),
                Arguments.of("NIF", List.of("alta", "renove", "migracion", "cambiotarifa", "portabilidad"), "España", "empresa"),
                Arguments.of("OTHERS", List.of("alta"), "España", "residencial"),
                Arguments.of("NIF", List.of("modificacion"), "España", "empresa"),
                Arguments.of("NIF", List.of("alta"), "España", "autonomo"),
                Arguments.of("NIF", List.of("alta"), "España", "empresa"),
                Arguments.of("NIF", List.of("alta"), "España", "residencial")
        );
    }

    private static Stream<Arguments> withInvalidNationality() {
        return Stream.of(
                Arguments.of("NIF", List.of("alta"), "Leganes", "empresa"),
                Arguments.of("NIF", List.of("alta"), "12345", "empresa")
        );
    }

    @ParameterizedTest
    @MethodSource({"withValidValues"})
    void getSignatureTypesWithValidValuesThenReturnOK(final String documentType, final List<String> commercialAct, final String nationality, final String segment) {

        List<Signature> signatures = buildSignatureList(commercialAct.size());

        when(nationalityPort.existNationality(nationality)).thenReturn(Boolean.TRUE);
        when(signaturePort.getSignatureTypes(any(SignatureDataInput.class))).thenReturn(signatures);

        SignatureTypes signatureTypes = signatureUseCase.getSignatureTypes(documentType, commercialAct, nationality, segment);

        Assertions.assertTrue(signatureTypes.allowedManualSignature());
        Assertions.assertTrue(signatureTypes.allowedDigitalSignature());
    }

    @ParameterizedTest
    @MethodSource("withInvalidNationality")
    void getSignatureTypesWithInvalidNationalityThrowsBadRequestException(final String documentType, final List<String> commercialAct, final String nationality, final String segment) {
        assertThrows(OpenApiBadRequestException.class, () -> signatureUseCase.getSignatureTypes(documentType, commercialAct, nationality, segment));
    }

    private static List<Signature> buildSignatureList(Integer size) {

      return switch (size) {
            case 1 -> Collections.singletonList(new Signature(1L, "España", "NIF", "empresa","alta"));
            case 2 -> List.of(new Signature(1L, "España", "NIF", "empresa","alta"),
                    new Signature(2L, "España", "NIF", "empresa","renove"));
            case 3 -> List.of(new Signature(1L, "España", "NIF", "empresa","alta"),
                    new Signature(2L, "España", "NIF", "empresa","renove"),
                    new Signature(1L, "España", "NIF", "empresa","migracion"));
            case 4 -> List.of(new Signature(1L, "España", "NIF", "empresa","alta"),
                    new Signature(2L, "España", "NIF", "empresa","renove"),
                    new Signature(1L, "España", "NIF", "empresa","migracion"),
                    new Signature(1L, "España", "NIF", "empresa","cambiotarifa"));
            case 5 -> List.of(new Signature(1L, "España", "NIF", "empresa","alta"),
                    new Signature(2L, "España", "NIF", "empresa","renove"),
                    new Signature(1L, "España", "NIF", "empresa","migracion"),
                    new Signature(1L, "España", "NIF", "empresa","cambiotarifa"),
                    new Signature(1L, "España", "NIF", "empresa","portabilidad"));
            default -> Collections.emptyList();
        };

    }

}

