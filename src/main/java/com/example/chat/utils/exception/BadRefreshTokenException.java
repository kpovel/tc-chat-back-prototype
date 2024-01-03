package com.example.chat.utils.exception;

import javax.security.auth.login.LoginException;

public class BadRefreshTokenException extends LoginException {
    public BadRefreshTokenException(String msg) {
        super(msg);
    }
}
