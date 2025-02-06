package com.orange.shoppingcart.signature.domain.model;

import com.orange.openapiosp.boot.errorhandler.exception.OpenApiBadRequestException;


import java.util.List;

public record SignatureDataInput(String documentType, List<String> commercialAct, String nationality, String segment) {

    public SignatureDataInput {

        if (nationality == null || nationality.isBlank()) {
            throw new OpenApiBadRequestException(OpenApiBadRequestException.ErrorMessageTypes.INVALID_URL_PARAMETER_VALUE, null);
        }

        if (DocumentType.isNotValidValue(documentType)) {
            throw new OpenApiBadRequestException(OpenApiBadRequestException.ErrorMessageTypes.INVALID_URL_PARAMETER_VALUE, null);
        }

        if (commercialAct == null || commercialAct.isEmpty() || CommercialAct.isNotValidValue(commercialAct)) {
            throw new OpenApiBadRequestException(OpenApiBadRequestException.ErrorMessageTypes.INVALID_URL_PARAMETER_VALUE, null);
        }

        if (Segment.isNotValidValue(segment)) {
            throw new OpenApiBadRequestException(OpenApiBadRequestException.ErrorMessageTypes.INVALID_URL_PARAMETER_VALUE, null);
        }
    }
}