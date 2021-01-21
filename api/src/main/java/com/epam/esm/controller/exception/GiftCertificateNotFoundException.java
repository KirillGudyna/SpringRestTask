package com.epam.esm.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "There is no such certificate")
public class GiftCertificateNotFoundException extends RuntimeException{

    public GiftCertificateNotFoundException(String message) {
        super(message);
    }
}
