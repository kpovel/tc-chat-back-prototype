package com.example.chat.exception;

import com.example.chat.validate.CustomFieldError;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
                new CustomFieldError("authorisation", messageSource.getMessage(ex.getMessage(), null, currentLocale)))));
    }
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<?> handleRefreshJwtAccessTokenExceptions(AuthException ex) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<>(List.of(
                new CustomFieldError("authorisation", messageSource.getMessage("user.bad.authorisation", null, currentLocale)))));
    }


    @ExceptionHandler(UserAccountNotActivatedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<?> emailNotVerificationException(UserAccountNotActivatedException ex) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ArrayList<>(List.of(
                new CustomFieldError("authorisation", messageSource.getMessage(ex.getMessage(), null, currentLocale)))));
    }
}
