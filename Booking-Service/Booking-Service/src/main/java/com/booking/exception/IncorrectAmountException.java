package com.booking.exception;

public class IncorrectAmountException extends RuntimeException {

    public IncorrectAmountException() {
        super();
    }

    public IncorrectAmountException(String s) {
        super(s);
    }
}
