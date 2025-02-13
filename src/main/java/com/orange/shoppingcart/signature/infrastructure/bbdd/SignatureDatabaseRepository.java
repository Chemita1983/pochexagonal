package com.orange.shoppingcart.signature.infrastructure.bbdd;

import com.orange.shoppingcart.signature.domain.model.Signature;
import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import com.orange.shoppingcart.signature.domain.ports.output.SignaturePort;
import com.orange.shoppingcart.signature.infrastructure.bbdd.model.SignatureEntity;
import com.orange.shoppingcart.signature.infrastructure.mapper.SignatureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SignatureDatabaseRepository implements SignaturePort {

    private final SignatureTypesJPARepository signatureTypesRepository;

    private final SignatureMapper signatureMapper;

    @Override
    public List<Signature> getSignatureTypes(SignatureDataInput signatureDataInput) {

        final List<SignatureEntity> rowsFounded = signatureTypesRepository.findByFilters(
                signatureDataInput.nationality(),
                signatureDataInput.commercialAct(),
                signatureDataInput.documentType(),
                signatureDataInput.segment());

        return signatureMapper.toSignatures(rowsFounded);
    }
}
