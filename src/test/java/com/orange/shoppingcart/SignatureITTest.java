package com.orange.shoppingcart;

import com.orange.openapi.api.model.ErrorMessage;
import com.orange.openapi.api.model.Signature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class SignatureITTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @ParameterizedTest
    @ValueSource(strings = {
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&nationality=España&segment=residencial",
            "/signature/signatureTypeAllowed?documentType=TODOS&commercialAct=TODOS&nationality=Francia&segment=TODOS",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&commercialAct=renove&nationality=Alemania&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&nationality=Alemania&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=renove&nationality=Alemania&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&commercialAct=renove&nationality=Alemania&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&commercialAct=migracion&nationality=España&segment=residencial",
    })
    public void givenValidSignature_whenGettingSignature_thenAllowManualSignature(String url) {
        ResponseEntity<Signature> response = restTemplate.getForEntity(url, Signature.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Signature responseBody = response.getBody();
        validateOKManualSignature(responseBody);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/signature/signatureTypeAllowed?documentType=PASAPORTE&commercialAct=TODOS&nationality=España&segment=residencial",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=TODOS&nationality=España&segment=empresa",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&commercialAct=migracion&nationality=Alemania&segment=residencial",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=TODOS&nationality=Alemania&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&nationality=Andorra&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=CIF&commercialAct=TODOS&nationality=Andorra&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=CIF&commercialAct=alta&commercialAct=renove&nationality=Andorra&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=CIF&commercialAct=alta&nationality=Andorra&segment=empresa",

    })
    public void givenValidSignature_whenGettingSignature_thenNotAllowManualSignature(String url) {
        ResponseEntity<Signature> response = restTemplate.getForEntity(url, Signature.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Signature responseBody = response.getBody();
        validateKOManualSignature(responseBody);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/signature/signatureTypeAllowed?documentType=&commercialAct=alta&commercialAct=baja&nationality=España&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=null&commercialAct=alta&nationality=España&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=CARNET DE CONDUCIR&commercialAct=alta&nationality=España&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=&commercialAct=baja&nationality=España&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=captura&nationality=España&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=null&nationality=España&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&nationality=Mordor&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&nationality=&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&nationality=null&segment=autonomo",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&nationality=España&segment=",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&nationality=null&segment=null",
            "/signature/signatureTypeAllowed?documentType=NIF&commercialAct=alta&nationality=null&segment=segmento",
    })
    public void givenInvalidSignatureWithInvalidNationality_whenGettingSignature_thenThrowsException(String url) {
        ResponseEntity<ErrorMessage> response = restTemplate.getForEntity(url, ErrorMessage.class);
        validateBadRequestResponse(response);
    }

    private void validateOKManualSignature(Signature responseBody) {
        Assertions.assertNotNull(responseBody);
        assertTrue(responseBody.getAllowedManualSignature());
    }

    private void validateKOManualSignature(Signature responseBody) {
        Assertions.assertNotNull(responseBody);
        assertFalse(responseBody.getAllowedManualSignature());
    }

    private void validateBadRequestResponse(ResponseEntity<ErrorMessage> response) {
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Invalid URL parameter value", response.getBody().getMessage());
    }
}
