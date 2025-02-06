package com.orange.shoppingcart.signature.domain.ports.input;

import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import com.orange.shoppingcart.signature.domain.model.SignatureTypes;

public interface SignatureUseCasePort {

    SignatureTypes getSignatureTypes(SignatureDataInput signatureDataInput);
}
