package com.example.demo.servise;

import com.example.demo.payload.response.JwtResponse;

import com.example.demo.sequrity.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;


//    public JwtResponse getAccessToken(@NonNull String refreshToken) {
//        if (jwtUtils.validateRefreshToken(refreshToken)) {
//            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
//            final String login = claims.getSubject();
//            final String saveRefreshToken = refreshStorage.get(login);
//            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
//                final User user = userService.getByLogin(login)
//                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
//                final String accessToken = jwtProvider.generateAccessToken(user);
//                return new JwtResponse(accessToken, null);
//            }
//        }
//        return new JwtResponse(null, null);
//    }
//
//    public JwtResponse refresh(@NonNull String refreshToken) {
//        if (jwtUtils.validateRefreshToken(refreshToken)) {
//            final Claims claims = jwtUtils.getRefreshClaims(refreshToken);
//            final String login = claims.getSubject();
//            final String saveRefreshToken = refreshStorage.get(login);
//            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
//                final User user = userService.getByLogin(login)
//                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
//                final String accessToken = jwtProvider.generateAccessToken(user);
//                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
//                refreshStorage.put(user.getLogin(), newRefreshToken);
//                return new JwtResponse(accessToken, newRefreshToken);
//            }
//        }
//        throw new AuthException("Невалидный JWT токен");
//    }

}
