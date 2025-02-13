package com.orange.shoppingcart.signature.domain.ports.output;

import com.orange.shoppingcart.signature.domain.model.Signature;
import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;

import java.util.List;

public interface SignaturePort {

    List<Signature> getSignatureTypes(SignatureDataInput signatureDataInput);
}
