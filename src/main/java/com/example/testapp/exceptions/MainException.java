package com.example.testapp.exceptions;

import lombok.Getter;

@Getter
public class MainException extends RuntimeException {

    private final MainError error;

    public MainException(MainError error, String message) {
        super(message);
        this.error = error;
    }

    public MainException (Exception exception) {
        if (exception.getClass()
                .isAssignableFrom(MainException.class)) {
            throw (MainException) exception;
        }
        throw new MainException(MainError.UNKNOWN_ERROR, exception.getMessage());
    }
}
