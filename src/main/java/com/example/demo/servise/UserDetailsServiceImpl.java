package com.example.demo.servise;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.sequrity.UserDetailsImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserLogin(login);
        if (login.contains("@")) {
            userOptional = userRepository.findByEmail(login);
        }
        if (userOptional.isEmpty()) {
            throw new BadCredentialsException(" ");
        }
        return new UserDetailsImpl(userOptional.get());
    }
}
