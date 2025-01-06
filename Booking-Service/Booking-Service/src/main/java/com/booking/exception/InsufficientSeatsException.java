package com.booking.exception;

public class InsufficientSeatsException extends RuntimeException {

    public InsufficientSeatsException() {
        super();
    }

    public InsufficientSeatsException(String s) {
        super(s);
    }

}
