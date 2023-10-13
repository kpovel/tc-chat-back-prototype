package com.example.demo.controller;

import com.example.demo.payload.request.SignupRequest;
import com.example.demo.servise.UserService;
import com.example.demo.validate.CustomFieldError;
import com.example.demo.validate.ValidateUserField;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ValidateUserField validate;
    private final MessageSource messageSource;

    @Operation(summary = "Registration new user")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,
                                          BindingResult bindingResult, @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag("en"));
        if (acceptLanguage != null && acceptLanguage.equals("uk-UA")) LocaleContextHolder.setLocale(Locale.forLanguageTag("uk"));
        Locale currentLocale = LocaleContextHolder.getLocale();
        if (bindingResult.hasErrors()) {
            try {
                List<CustomFieldError> errorFields = bindingResult.getFieldErrors().stream()
                        .map(fieldError -> new CustomFieldError(fieldError.getField(), messageSource.getMessage(fieldError.getDefaultMessage(), null, currentLocale)))
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorFields);
            } catch (NoSuchMessageException e) {
                return ResponseEntity.badRequest().body(new ArrayList<>(List.of(
                        new CustomFieldError("serverError", messageSource.getMessage("server.error", null, currentLocale)))));
            }
        }
        if (validate.existsByLogin(signUpRequest.getLogin())) {
            return ResponseEntity.badRequest().body(new ArrayList<>(List.of(
                    new CustomFieldError("loginDuplicate", messageSource.getMessage("login.duplicate", null, currentLocale)))));
        }
        if (validate.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ArrayList<>(List.of(
                    new CustomFieldError("emailDuplicate", messageSource.getMessage("email.duplicate", null, currentLocale)))));
        }
        userService.createUser(signUpRequest);
        return ResponseEntity.ok(messageSource.getMessage("user.signup.success", null, currentLocale));
    }


    @GetMapping()
    public ResponseEntity<String> mainPage() {
        return ResponseEntity.ok("It is Main page - no protected");
    }

    @GetMapping("/chat")
    public ResponseEntity<String> securityPage() {
        return ResponseEntity.ok("It is Chat page - protected");
    }

}
