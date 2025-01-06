package com.booking.exception;

public class BookingNotFoundException extends RuntimeException {

	public BookingNotFoundException() {
		super();
	}

	public BookingNotFoundException(String s) {
		super(s);
	}
}
