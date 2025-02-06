package com.orange.shoppingcart.signature.infrastructure.mapper;

import com.orange.openapi.api.model.Signature;
import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import com.orange.shoppingcart.signature.domain.model.SignatureTypes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SignatureMapper {

    Signature toSignature(SignatureTypes signatureTypes);

    SignatureDataInput toSignatureDataInput(String documentType, List<String> commercialAct, String nationality, String segment);

}
