package com.example.chat.payload.response;

import com.example.chat.validate.CustomFieldError;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ParserToResponseFromCustomFieldError {

    private Map<String, String> fieldErrors;


    public static Map<String, String> parseCustomFieldErrors(List<CustomFieldError> collection) {
        Map<String, String> fieldErrors = new HashMap<>();

        for (CustomFieldError arr : collection) {
            String fieldName = arr.getFieldName();
            String fieldMessage = arr.getFieldMessage();

            // Використовуємо merge для додавання значень до fieldErrors
            fieldErrors.merge(fieldName, fieldMessage, (existingValue, newValue) -> existingValue + ", " + newValue);
        }

        return fieldErrors;
    }
}