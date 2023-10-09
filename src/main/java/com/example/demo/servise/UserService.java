package com.example.demo.servise;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createUser(SignupRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        if (userRepository.findByEmail(email).isPresent()) return false;
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getAuthority().add(Role.ROLE_USER);
        user.setEnable(true);
        userRepository.save(user);
        return true;
    }
}
