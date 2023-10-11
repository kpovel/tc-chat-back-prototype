package com.example.demo.controller;

import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.sequrity.UserDetailsImpl;
import com.example.demo.sequrity.jwt.JwtUtils;
import com.example.demo.servise.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api")
@AllArgsConstructor

public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwtAccessToken = jwtUtils.generateJwtToken(authentication);
        final String jwtRefreshToken = jwtUtils.generateRefreshToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Cookie cookie = new Cookie("myCookie", jwtAccessToken);
        cookie.setHttpOnly(true); // Встановлюємо атрибут HTTPOnly

//         Додавання cookie до HTTP-відповіді
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(jwtAccessToken, jwtRefreshToken));
    }





//    @PostMapping("token")
//    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
//        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
//        return ResponseEntity.ok(token);
//    }
//
//    @PostMapping("refresh")
//    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
//        final JwtResponse token = authService.refresh(request.getRefreshToken());
//        return ResponseEntity.ok(token);
//    }
}
