package com.avolkov.exceptions;

public class CovidException extends Exception {
    public CovidException() {
    }

    public CovidException(String message) {
        super(message);
    }

    public CovidException(String message, Throwable cause) {
        super(message, cause);
    }

    public CovidException(Throwable cause) {
        super(cause);
    }

    public CovidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
