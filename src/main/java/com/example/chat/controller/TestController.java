package com.example.chat.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;

@RestController
public class TestController {

    @GetMapping("/")
    public ResponseEntity<String> mainPage(HttpServletRequest request) {

        String originHeader = request.getHeader("Origin");
        if (originHeader == null) {
            originHeader = request.getHeader("Referer");
        }
        return ResponseEntity.ok("It is Main page - no protected. Domain: " + originHeader);
    }
}
