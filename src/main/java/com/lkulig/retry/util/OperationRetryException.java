package com.lkulig.retry.util;

public class OperationRetryException extends RuntimeException {

    private static final long serialVersionUID = 3026958915508551649L;
    private static final String MESSAGE = "Error during operation retry";

    public OperationRetryException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
