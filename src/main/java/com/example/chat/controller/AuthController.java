package com.example.chat.controller;

import com.example.chat.payload.request.LoginRequest;
import com.example.chat.payload.response.JwtResponse;
import com.example.chat.sequrity.jwt.JwtUtils;
import com.example.chat.sequrity.UserDetailsImpl;
import com.example.chat.servise.AuthService;
import com.example.chat.validate.CustomFieldError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@Tag(name = "JWT Tokens", description = " ")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final AuthService authService;


    @PostMapping("/login")
    @Operation(summary = "User authentication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = CustomFieldError.class), mediaType = "application/json") })
             })
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


    @PostMapping("/refresh/access-token")
    @Operation(summary = "Refresh jwt access token", description = "request: json - refresh token in body, headers - access token; response - json: new access token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = CustomFieldError.class), mediaType = "application/json") })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody JwtResponse request) throws AuthException {
        final JwtResponse token = authService.getAccessToken(request.getJwtRefreshToken());
        return ResponseEntity.ok(token);
    }


    @PostMapping("/refresh/refresh-token")
    @Operation(summary = "Refresh jwt refresh token", description = "request: json - refresh token in body, headers - access token; response - json: new access and refresh tokens")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = CustomFieldError.class), mediaType = "application/json") })
    })
    @SecurityRequirement(name = "Bearer Authentication")
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
