package org.example.movieapi.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

//    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Data integrity violation")
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public void handleDataIntegrityException(){}

    // return type: can be another DTO + @ResponseStatus
    // or ResponseEntity with DTO + Http status
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityException(DataIntegrityViolationException exception){
        var problemDetail =  ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setProperty("message", exception.getMessage());
        return problemDetail;
    }
}
