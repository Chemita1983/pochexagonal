package com.orange.shoppingcart.signature.infrastructure.exception;

import com.orange.openapi.api.model.ErrorMessage;
import com.orange.openapiosp.boot.errorhandler.exception.OpenApiBadRequestException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

@Slf4j
@ControllerAdvice
public class SignatureControllerAdvice {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> handleException(ConstraintViolationException ex, ServletWebRequest request) {

        log.error("Validation error when calling WebService: {}", ex.getMessage(), ex);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final String customErrorCode = OpenApiBadRequestException.ErrorMessageTypes.INVALID_URL_PARAMETER_VALUE.toString();

        ErrorMessage errorMessage =
                new ErrorMessage(customErrorCode,
                        ex.getMessage(),
                        status.getReasonPhrase(),
                        null);

        return new ResponseEntity<>(errorMessage, headers, status);
    }
}
