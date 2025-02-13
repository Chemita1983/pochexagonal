package com.orange.shoppingcart.signature.application;

import com.orange.shoppingcart.signature.domain.model.SignatureTypes;

import java.util.List;

public interface SignatureUseCasePort {

    SignatureTypes getSignatureTypes(final String documentType, final List<String> commercialAct, final String nationality, final String segment);
}
