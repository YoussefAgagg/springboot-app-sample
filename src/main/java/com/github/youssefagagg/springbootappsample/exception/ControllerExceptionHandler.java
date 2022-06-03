package com.github.youssefagagg.springbootappsample.exception;

import com.github.youssefagagg.springbootappsample.web.rest.exception.BadRequestAlertException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler
         {


    @ExceptionHandler(AccountActivationException.class)
    protected String handleAccountActivationException() {
        return "/login";
    }


}
