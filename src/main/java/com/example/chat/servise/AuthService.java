package com.example.chat.servise;

import com.example.chat.utils.exception.BadRefreshTokenException;
import com.example.chat.model.User;
import com.example.chat.payload.response.JwtResponse;
import com.example.chat.sequrity.UserDetailsImpl;
import com.example.chat.sequrity.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;

    private final UserService userService;

    private final Map<String, String> refreshStorage = new HashMap<>();

    public void saveJwtRefreshTokenToStorage(String userEmail, String jwtRefreshToken){
        refreshStorage.put(userEmail, jwtRefreshToken);
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException, BadRefreshTokenException {
        if (jwtUtils.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtUtils.getRefreshClaims(refreshToken);
            final String userEmail = claims.getSubject();
//            Integer id = (Integer)claims.get("id"); // Id User
            final String saveRefreshToken = refreshStorage.get(userEmail);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Authentication authentication = userService.userAuthentication(new User(userEmail));
                if (authentication == null) throw new AuthException("User authentication error");

                final String accessToken = jwtUtils.generateJwtAccessToken(authentication);

                return new JwtResponse(accessToken, null);
            }
        }
        throw new BadRefreshTokenException("It is not correct refresh token");
    }


    public JwtResponse getJwtRefreshToken(@NonNull String refreshToken) throws AuthException, BadRefreshTokenException {
        if (jwtUtils.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtUtils.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null) throw new AuthException("User authentication error");

                final String accessToken = jwtUtils.generateJwtAccessToken(authentication);
                final String newRefreshToken = jwtUtils.generateRefreshToken(authentication);
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                refreshStorage.put(userDetails.getUsername(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new BadRefreshTokenException("It is not correct refresh token");
    }

}
