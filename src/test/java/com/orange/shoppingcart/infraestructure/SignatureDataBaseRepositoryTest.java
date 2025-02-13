package com.orange.shoppingcart.infraestructure;


import com.orange.shoppingcart.signature.infrastructure.bbdd.SignatureTypesJPARepository;
import com.orange.shoppingcart.signature.infrastructure.bbdd.model.SignatureEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Stream;

@DataJpaTest
public class SignatureDataBaseRepositoryTest {

    private static Stream<Arguments> withValidValues() {
        return Stream.of(
                Arguments.of("NIF", List.of("alta"), "España", "empresa"),
                Arguments.of("NIF", List.of("alta"), "España", "autonomo"),
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
                Arguments.of("NIF", List.of("alta"), "", "autonomo"),
                Arguments.of("NIF", List.of("alta"), null, "autonomo"),
                Arguments.of("TODOS", List.of("TODOS"), "España", "JUAN"),
                Arguments.of("TODOS", List.of("TODOS"), "España", null),
                Arguments.of("TODOS", List.of("TODOS"), "España", "")
        );
    }

    @Autowired
    SignatureTypesJPARepository signatureTypesJPARepository;

    @ParameterizedTest
    @MethodSource("withValidValues")
    void getSignatureTypesAllowedGivenValidValuesThenReturnRows(final String documentType, final List<String> commercialAct, final String nationality, final String segment) {

        List<SignatureEntity> signatureEntities = signatureTypesJPARepository.findByFilters(nationality, commercialAct, documentType, segment);
        Assertions.assertNotNull(signatureEntities);
        Assertions.assertFalse(signatureEntities.isEmpty());
    }


    @ParameterizedTest
    @MethodSource("withInvalidValues")
    void getSignatureTypesAllowedGivenValidValuesThenNoReturnRows(final String documentType, final List<String> commercialAct, final String nationality, final String segment) {

        List<SignatureEntity> signatureEntities = signatureTypesJPARepository.findByFilters(nationality, commercialAct, documentType, segment);
        Assertions.assertNotNull(signatureEntities);
        Assertions.assertTrue(signatureEntities.isEmpty());
    }
}
