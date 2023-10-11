package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.servise.UserService;
import com.example.demo.validate.CustomFieldError;
import com.example.demo.validate.ValidateUserField;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ValidateUserField validate;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<CustomFieldError> errors = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> new CustomFieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
//        if (!validate.validateForSpaces(signUpRequest.getPassword())) {
//            return ResponseEntity.badRequest().body(new ArrayList<>(Arrays.asList(new CustomFieldError("password", "В полі password не повинно бути пропусків"))));
//        }
//        if (!validate.validateForSpaces(signUpRequest.getLogin())) {
//            return ResponseEntity.badRequest().body(new ArrayList<>(Arrays.asList(new CustomFieldError("login", "В полі login не повинно бути пропусків"))));
//        }
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
