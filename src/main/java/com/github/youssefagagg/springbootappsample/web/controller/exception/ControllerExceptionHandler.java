package com.github.youssefagagg.springbootappsample.web.controller.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler
         {


    @ExceptionHandler(AccountActivationException.class)
    protected String handleAccountActivationException() {
        return "/login";
    }


}
