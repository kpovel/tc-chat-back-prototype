package com.example.demo.controller;

import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.sequrity.UserDetailsImpl;
import com.example.demo.sequrity.jwt.JwtUtils;
import com.example.demo.servise.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api")
@Tag(name = " ", description = " ")
@AllArgsConstructor

public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final AuthService authService;


    @Operation(summary = "User authorisation")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                              HttpServletResponse response,
                                              @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag("en"));
        if (acceptLanguage != null && acceptLanguage.equals("uk-UA")) LocaleContextHolder.setLocale(Locale.forLanguageTag("uk"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwtAccessToken = jwtUtils.generateJwtAccessToken(authentication);
        final String jwtRefreshToken = jwtUtils.generateRefreshToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        authService.saveJwtRefreshTokenToStorage(userDetails.getUsername(), jwtRefreshToken);

        Cookie cookie = new Cookie("myCookie", jwtRefreshToken);
        cookie.setHttpOnly(true); // Встановлюємо атрибут HTTPOnly

        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(jwtAccessToken, jwtRefreshToken));
    }

    @Operation(summary = "Refresh jwt access token")
    @PostMapping("/refresh/access-token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody JwtResponse request) throws AuthException {
        final JwtResponse token = authService.getAccessToken(request.getJwtRefreshToken());
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Refresh jwt refresh token", description = " des", tags = {" tags "})
    @PostMapping("/refresh/refresh-token")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody JwtResponse request) throws AuthException {
        final JwtResponse token = authService.getJwtRefreshToken(request.getJwtRefreshToken());
        return ResponseEntity.ok(token);
    }

//    @RequestMapping("/your-endpoint")
//    public ResponseEntity<?> yourEndpoint(HttpServletRequest request, HttpServletResponse response) {
//        // Отримуємо куку з запиту
//        Cookie[] cookies = request.getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("myCookie".equals(cookie.getName())) {
//                    // Отримуємо значення куки
//                    String jwtRefreshToken = cookie.getValue();
//
//                    // Оновлюємо значення, якщо потрібно
//
//                    // Створюємо нову куку з оновленим значенням
//                    Cookie updatedCookie = new Cookie("myCookie", newRefreshTokenValue);
//                    updatedCookie.setHttpOnly(true); // Встановлюємо атрибут HTTPOnly
//
//                    // Відправляємо оновлену куку відповіддю
//                    response.addCookie(updatedCookie);
//
//                    // Решта логіки вашого контролера
//                }
//            }
//        }
//
//        // Решта вашого контролера
//        return ResponseEntity.ok("Your response here");
//    }
}
