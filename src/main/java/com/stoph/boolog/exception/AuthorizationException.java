package com.stoph.boolog.exception;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends BoologException {

    private static final String MESSAGE = "접근 권한이 없습니다.";

    public AuthorizationException() {
        super(MESSAGE);
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatus() {
        return HttpStatus.FORBIDDEN.value();
    }
}
