package com.toyproject.kithub.exception;

public class NotEnoughStockException extends RuntimeException {

    private String message;

    public NotEnoughStockException(String message) {
        super(message);
        this.message = message;
    }
}
