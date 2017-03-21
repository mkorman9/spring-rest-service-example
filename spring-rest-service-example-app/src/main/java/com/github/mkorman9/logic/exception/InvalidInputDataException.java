package com.github.mkorman9.logic.exception;

public class InvalidInputDataException extends RuntimeException {
    private Object error;

    public InvalidInputDataException(Object error) {
        this.error = error;
    }

    public Object getError() {
        return error;
    }
}
