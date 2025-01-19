package com.api_gateway.exception;

public class InvalidAuthorizationHeader extends RuntimeException {

    public InvalidAuthorizationHeader() {
        super();
    }

    public InvalidAuthorizationHeader(String s) {
        super(s);
    }

}
