package com.example.demo.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAccountNotActivatedException extends AuthenticationException {
    public UserAccountNotActivatedException(String message) {
        super(message);
    }
}
