package com.stoph.boolog.exception;

import org.springframework.http.HttpStatus;

public class ExistNameException extends BoologException {

    private static final String MESSAGE = "이미 존재하는 이름입니다.";

    public ExistNameException() {
        super(MESSAGE);
    }

    public ExistNameException(String message) {
        super(message);
    }

    @Override
    public int getStatus() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
