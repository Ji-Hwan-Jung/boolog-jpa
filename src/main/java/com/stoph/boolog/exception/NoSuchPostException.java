package com.stoph.boolog.exception;

import org.springframework.http.HttpStatus;

public class NoSuchPostException extends BoologException {

    private static final String MESSAGE = "존재하지 않는 게시글입니다.";

    public NoSuchPostException() {
        super(MESSAGE);
    }

    public NoSuchPostException(String message) {
        super(message);
    }

    @Override
    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}
