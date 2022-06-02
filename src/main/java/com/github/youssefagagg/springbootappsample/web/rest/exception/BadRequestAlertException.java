package com.github.youssefagagg.springbootappsample.web.rest.exception;

public class BadRequestAlertException extends RuntimeException {
    public BadRequestAlertException(String message) {
        super(message);
    }
}
