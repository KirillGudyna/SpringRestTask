package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongParameterFormatException extends AbstractRuntimeException {

    public WrongParameterFormatException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
