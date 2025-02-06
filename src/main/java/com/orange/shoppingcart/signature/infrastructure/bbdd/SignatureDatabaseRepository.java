package com.orange.shoppingcart.signature.infrastructure.bbdd;

import com.orange.shoppingcart.signature.infrastructure.bbdd.model.SignatureEntity;
import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import com.orange.shoppingcart.signature.domain.model.SignatureTypes;
import com.orange.shoppingcart.signature.domain.ports.output.SignaturePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SignatureDatabaseRepository implements SignaturePort {

    private static final String TODOS_VALUE = "TODOS";

    private final SignatureTypesJPARepository signatureTypesRepository;

    @Override
    public SignatureTypes getSignatureTypes(SignatureDataInput signatureDataInput) {

        final List<SignatureEntity> rowsFounded = signatureTypesRepository.findByFilters(
                signatureDataInput.nationality(),
                signatureDataInput.commercialAct(),
                signatureDataInput.documentType(),
                signatureDataInput.segment());

        return this.allowedManualSignature(rowsFounded, signatureDataInput.commercialAct());

    }

    private SignatureTypes allowedManualSignature(List<SignatureEntity> rowsFounded, final List<String> commercialActs) {
        boolean hasRows = !rowsFounded.isEmpty();

        if(hasRows) {
            boolean hasAllCommercialActs = rowsFounded.getFirst().getCommercialAct().contains(TODOS_VALUE);
            boolean isCommercialActValid = commercialActs.size() <= rowsFounded.size();
            boolean isManualSignatureAllowed = hasAllCommercialActs || isCommercialActValid;
            return new SignatureTypes(isManualSignatureAllowed, true);
        }

        return new SignatureTypes(false, true);
    }

}
