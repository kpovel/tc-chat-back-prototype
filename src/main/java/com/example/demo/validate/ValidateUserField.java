package com.example.demo.validate;

import org.springframework.stereotype.Component;

@Component
public class ValidateUserField {
    public boolean validateUserPassword(String password) {
        return false;
    }

    public boolean validateForSpaces (String field) {
        String newField = field.trim();
        newField = newField.replaceAll(" ", "");
        if (field.equals(newField)) return true;
        return false;
    }
}
