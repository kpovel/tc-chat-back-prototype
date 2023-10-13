package com.example.demo.exception;

import com.example.demo.validate.CustomFieldError;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<?> handleUserAuthorisationExceptions(BadCredentialsException ex) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<>(List.of(
                new CustomFieldError("authorisation", messageSource.getMessage("user.bad.authorisation", null, currentLocale)))));
    }
}
