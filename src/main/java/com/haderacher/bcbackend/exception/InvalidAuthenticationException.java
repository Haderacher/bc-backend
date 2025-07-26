package com.haderacher.bcbackend.exception;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException() {
        super("invalid username or password");
    }
}
