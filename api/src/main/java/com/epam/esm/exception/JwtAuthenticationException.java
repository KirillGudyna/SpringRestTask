package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    private HttpStatus status;

    public JwtAuthenticationException(String explanation, HttpStatus status) {
        super(explanation);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
