package com.stoph.boolog.exception;

public abstract class BoologException extends RuntimeException{

    public BoologException() {
        super();
    }

    public BoologException(String message) {
        super(message);
    }

    public BoologException(Throwable cause) {
        super(cause);
    }

    public BoologException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatus();
}
