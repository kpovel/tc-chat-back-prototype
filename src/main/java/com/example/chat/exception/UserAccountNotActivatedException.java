package com.example.chat.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAccountNotActivatedException extends AuthenticationException {
    public UserAccountNotActivatedException(String message) {
        super(message);
    }
}
