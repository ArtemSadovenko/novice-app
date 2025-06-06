package com.novice.debatenovice.exeptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlerService {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleServiceException(final MethodArgumentNotValidException ex) {

        final String messages = //"Wrong input data";
                ex.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .reduce((s1, s2) -> s1 + "; " + s2)
                        .orElse("We have an issue with creating error message");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDTO(messages));
    }


    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Object> handleServiceException(final CustomException ex, final WebRequest request) {
        return ResponseEntity
                .status(ex.getHttpStatus() == null ? HttpStatus.BAD_REQUEST : ex.getHttpStatus())
                .body(new ExceptionDTO(ex.getMessage()));
    }



}
