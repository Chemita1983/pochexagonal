package com.orange.shoppingcart.signature.infrastructure.api;

import com.orange.openapi.api.SignatureApi;
import com.orange.openapi.api.model.Signature;
import com.orange.shoppingcart.signature.domain.model.SignatureDataInput;
import com.orange.shoppingcart.signature.domain.model.SignatureTypes;
import com.orange.shoppingcart.signature.domain.ports.input.SignatureUseCasePort;
import com.orange.shoppingcart.signature.infrastructure.mapper.SignatureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class SignatureController implements SignatureApi {

    private final SignatureUseCasePort signatureUseCasePort;

    private final SignatureMapper signatureMapper;

    @Override
    @Valid
    public ResponseEntity<Signature> getSignatureTypeAllowed(final String documentType, final List<String> commercialAct, final String nationality, final String segment) {

        final SignatureDataInput signatureDataInput = signatureMapper.toSignatureDataInput(documentType, commercialAct, nationality, segment);

        SignatureTypes signatureTypes = this.signatureUseCasePort.getSignatureTypes(signatureDataInput);

        return new ResponseEntity<>(this.signatureMapper.toSignature(signatureTypes), HttpStatus.OK);
    }
}
