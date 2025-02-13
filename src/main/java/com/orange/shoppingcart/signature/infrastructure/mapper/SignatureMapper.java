package com.orange.shoppingcart.signature.infrastructure.mapper;

import com.orange.shoppingcart.signature.domain.model.Signature;
import com.orange.shoppingcart.signature.domain.model.SignatureTypes;
import com.orange.shoppingcart.signature.infrastructure.bbdd.model.SignatureEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SignatureMapper {

    List<Signature> toSignatures(List<SignatureEntity> signatureEntity);

    com.orange.openapi.api.model.Signature toSignature(SignatureTypes signatureTypes);

}
