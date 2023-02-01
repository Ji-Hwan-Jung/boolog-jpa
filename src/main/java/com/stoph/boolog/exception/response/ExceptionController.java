package com.stoph.boolog.exception.response;

import com.stoph.boolog.exception.BoologException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(BoologException.class)
    public ResponseEntity<ErrorResponse> boologException(BoologException ex) {

        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(ex.getStatus()))
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatus()));
    }
}
