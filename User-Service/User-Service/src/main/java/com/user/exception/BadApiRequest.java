package com.user.exception;

public class BadApiRequest extends RuntimeException {

    public BadApiRequest(String s) {
        super(s);
    }

    public BadApiRequest() {
        super("Bad Api Request");
    }

}
