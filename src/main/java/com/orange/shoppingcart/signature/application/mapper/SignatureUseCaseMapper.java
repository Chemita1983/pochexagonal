package com.orange.shoppingcart.signature.application.mapper;

import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SignatureUseCaseMapper {

    SignatureDataInput toSignatureDataInput(String documentType, List<String> commercialAct, String nationality, String segment);
}
