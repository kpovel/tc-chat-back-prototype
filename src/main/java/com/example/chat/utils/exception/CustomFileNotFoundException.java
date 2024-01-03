package com.example.chat.utils.exception;

import java.io.FileNotFoundException;

public class CustomFileNotFoundException extends FileNotFoundException {
    public CustomFileNotFoundException(String message) {
        super(message);
    }
    public CustomFileNotFoundException(String filename, String message) {
        super(message);
    }
}
