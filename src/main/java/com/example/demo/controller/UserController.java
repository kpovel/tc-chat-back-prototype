package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.servise.UserService;
import com.example.demo.validate.CustomFieldError;
import com.example.demo.validate.ValidateUserField;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,
                                          BindingResult bindingResult, @RequestHeader("Accept-Language") String acceptLanguage) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag("en"));
        if(acceptLanguage.equals("uk-UA")) LocaleContextHolder.setLocale(Locale.forLanguageTag("uk"));
        Locale currentLocale = LocaleContextHolder.getLocale();
        if (bindingResult.hasErrors()) {
//            List<CustomFieldError> errors = bindingResult.getFieldErrors().stream()
//                    .map(fieldError -> new CustomFieldError(fieldError.getField(), fieldError.getDefaultMessage()))
//                    .collect(Collectors.toList());
            List<CustomFieldError> errors = null;
//            try {
                errors = bindingResult.getFieldErrors().stream()
                        .map(fieldError -> new CustomFieldError(fieldError.getField(), messageSource.getMessage(fieldError.getDefaultMessage(), null, currentLocale)))
                        .collect(Collectors.toList());
//            } catch (NoSuchMessageException e) {
//                System.out.println(e.getMessage());
                return ResponseEntity.badRequest().body(errors);
//            }

        }

        if (userService.createUser(signUpRequest)) {
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } else return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
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
