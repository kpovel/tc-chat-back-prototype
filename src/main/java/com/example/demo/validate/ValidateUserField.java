package com.example.demo.validate;

import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ValidateUserField {
    private final UserRepository userRepository;
    public boolean existsByLogin(String login) {
        return userRepository.existsByUserLogin(login);
    }

    public boolean existsByEmail (String email) {
        return userRepository.existsByEmail(email);
    }
}
