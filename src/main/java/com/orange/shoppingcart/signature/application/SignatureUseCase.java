package com.orange.shoppingcart.signature.application;

import com.orange.openapiosp.boot.errorhandler.exception.OpenApiBadRequestException;
import com.orange.shoppingcart.signature.application.mapper.SignatureUseCaseMapper;
import com.orange.shoppingcart.signature.domain.model.Signature;
import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import com.orange.shoppingcart.signature.domain.model.SignatureTypes;
import com.orange.shoppingcart.signature.domain.ports.output.NationalityPort;
import com.orange.shoppingcart.signature.domain.ports.output.SignaturePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignatureUseCase implements SignatureUseCasePort {

    private static final String TODOS_VALUE = "TODOS";

    private final NationalityPort nationalityPort;

    private final SignaturePort signaturePort;

    private final SignatureUseCaseMapper signatureUseCaseMapper;


    @Override
    public SignatureTypes getSignatureTypes(final String documentType, final List<String> commercialAct, final String nationality, final String segment) {

        final SignatureDataInput signatureDataInput = signatureUseCaseMapper.toSignatureDataInput(documentType, commercialAct, nationality, segment);

        if (!nationalityPort.existNationality(signatureDataInput.nationality())) {
            throw new OpenApiBadRequestException(OpenApiBadRequestException.ErrorMessageTypes.INVALID_URL_PARAMETER_VALUE, null);
        }

        List<Signature> signaturesFounded = signaturePort.getSignatureTypes(signatureDataInput);

        Boolean isAllowedManualSignature = this.allowedManualSignature(signaturesFounded, signatureDataInput.commercialAct());

        return new SignatureTypes(isAllowedManualSignature, Boolean.TRUE);
    }

    private Boolean allowedManualSignature(List<Signature> signaturesFounded, final List<String> commercialActs) {
        boolean hasRows = !signaturesFounded.isEmpty();

        if (hasRows) {
            boolean hasAllCommercialActs = signaturesFounded.getFirst().commercialAct().contains(TODOS_VALUE);
            boolean isCommercialActValid = commercialActs.size() <= signaturesFounded.size();
            return hasAllCommercialActs || isCommercialActValid;
        }

        return Boolean.FALSE;
    }
}
