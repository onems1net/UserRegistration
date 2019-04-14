package com.animesh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class UserControllerAdvice {
    @ResponseBody
    @ExceptionHandler({UserNotFoundException.class,UserAlreadyExistException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handler(RuntimeException ex) {
        return ex.getMessage();
    }
}
