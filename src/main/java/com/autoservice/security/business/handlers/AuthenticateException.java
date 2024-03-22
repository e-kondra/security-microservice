package com.autoservice.security.business.handlers;

public class AuthenticateException extends Exception{

    public AuthenticateException(String errorMessage) {
        super(errorMessage);
    }
}
