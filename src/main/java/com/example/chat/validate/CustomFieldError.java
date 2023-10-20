package com.example.chat.validate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomFieldError {
    private String fieldName;
    private String fieldMessage;


    public CustomFieldError(String fieldName, String fieldMessage) {
        this.fieldName = fieldName;
        this.fieldMessage = fieldMessage;
    }
}
