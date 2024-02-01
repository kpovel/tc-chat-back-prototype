package com.example.chat.utils.exception;


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
