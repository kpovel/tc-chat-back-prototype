package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.servise.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userService.createUser(signUpRequest)) {
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } else return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
    }



    @GetMapping()
    public ResponseEntity<String> mainPage(){
        return ResponseEntity.ok("It is Main page - no protected");
    }

    @GetMapping("/chat")
    public ResponseEntity<String> securityPage(){
        return ResponseEntity.ok("It is Chat page - protected");
    }

}
