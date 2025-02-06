package com.orange.shoppingcart.signature.domain.ports.output;

import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import com.orange.shoppingcart.signature.domain.model.SignatureTypes;

public interface SignaturePort {

    SignatureTypes getSignatureTypes(SignatureDataInput signatureDataInput);
}
