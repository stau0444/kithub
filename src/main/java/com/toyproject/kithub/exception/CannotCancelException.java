package com.toyproject.kithub.exception;


public class CannotCancelException extends RuntimeException {

    private String message;

    public CannotCancelException(String message) {
        super(message);
        this.message = message;
    }
}
