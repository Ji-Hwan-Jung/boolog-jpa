package com.stoph.boolog.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends BoologException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public AuthenticationException() {
        super(MESSAGE);
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatus() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
