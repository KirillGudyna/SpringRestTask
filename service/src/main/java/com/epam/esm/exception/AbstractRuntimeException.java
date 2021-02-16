package com.epam.esm.exception;

public abstract class AbstractRuntimeException extends RuntimeException {
    protected int errorCode;

    public AbstractRuntimeException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
