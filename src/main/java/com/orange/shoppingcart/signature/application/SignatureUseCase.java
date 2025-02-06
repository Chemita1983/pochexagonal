package com.orange.shoppingcart.signature.application;

import com.orange.openapiosp.boot.errorhandler.exception.OpenApiBadRequestException;
import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import com.orange.shoppingcart.signature.domain.model.SignatureTypes;
import com.orange.shoppingcart.signature.domain.ports.input.SignatureUseCasePort;
import com.orange.shoppingcart.signature.domain.ports.output.NationalityPort;
import com.orange.shoppingcart.signature.domain.ports.output.SignaturePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignatureUseCase implements SignatureUseCasePort {

    private final NationalityPort nationalityPort;

    private final SignaturePort signaturePort;

    @Override
    public SignatureTypes getSignatureTypes(SignatureDataInput signatureDataInput) {

        if (!nationalityPort.existNationality(signatureDataInput.nationality())) {
            throw new OpenApiBadRequestException(OpenApiBadRequestException.ErrorMessageTypes.INVALID_URL_PARAMETER_VALUE, null);
        }

        return this.signaturePort.getSignatureTypes(signatureDataInput);
    }
}
