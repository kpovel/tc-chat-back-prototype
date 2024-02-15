package com.example.chat.utils.validate;

import com.example.chat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ValidateFields {
    private final UserRepository userRepository;
    public boolean existsByLogin(String login) {
        return userRepository.existsByUserLogin(login);
    }

    public boolean existsByEmail (String email) {
        return userRepository.existsByEmail(email);
    }

    public static boolean isSupportedImageType(String contentType) {
        return contentType != null && (contentType.equalsIgnoreCase("image/jpeg") || contentType.equalsIgnoreCase("image/png")
                                        || contentType.equalsIgnoreCase("image/webp") || contentType.equalsIgnoreCase("image/jpg"));
    }
}
