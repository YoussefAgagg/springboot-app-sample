package com.github.youssefagagg.springbootappsample.exception;



public class UserNotActivatedException extends RuntimeException {
    public UserNotActivatedException(String  message) {
        super(message);
    }
}
