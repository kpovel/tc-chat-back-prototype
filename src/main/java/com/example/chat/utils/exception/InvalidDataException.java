package com.example.chat.utils.exception;

import javax.security.auth.login.LoginException;

public class InvalidDataException extends IllegalArgumentException {

    public InvalidDataException() {
        super("Invalid data provided.");
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
