package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.servise.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout) {
        if(error != null){
            return ResponseEntity.ok("login error");
        } else return ResponseEntity.ok("User logout");
    }

//    @PostMapping("/registration")
//    public ResponseEntity<String> registrationUser(@RequestBody User user) {
//        if(userService.createUser(user)) {
//            return ResponseEntity.ok("OK");
//        }
//        return ResponseEntity.notFound().build();
//    }

    @GetMapping("/")
    public ResponseEntity<String> mainPage(){
        return ResponseEntity.ok("main");
    }

    @GetMapping("/security-page")
    public ResponseEntity<String> securityPage(){
        return ResponseEntity.ok("securityPage");
    }

}
