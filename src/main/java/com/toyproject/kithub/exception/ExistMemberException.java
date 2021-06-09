package com.toyproject.kithub.exception;

public class ExistMemberException extends RuntimeException {

    private String message;

    public ExistMemberException(String message) {
        super(message);
        this.message = message;
    }
}
