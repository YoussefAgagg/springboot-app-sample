package com.github.youssefagagg.springbootappsample.web.controller.exception;



public class UserNotActivatedException extends RuntimeException {
    public UserNotActivatedException(String  message) {
        super(message);
    }
}
