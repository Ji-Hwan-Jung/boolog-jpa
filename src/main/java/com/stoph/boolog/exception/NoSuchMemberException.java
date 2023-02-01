package com.stoph.boolog.exception;

import org.springframework.http.HttpStatus;

public class NoSuchMemberException extends BoologException {

    private static final String MESSAGE = "존재하지 않는 회원입니다.";

    public NoSuchMemberException() {
        super(MESSAGE);
    }

    public NoSuchMemberException(String message) {
        super(message);
    }

    @Override
    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}
