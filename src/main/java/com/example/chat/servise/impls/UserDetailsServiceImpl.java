package com.example.chat.servise.impls;

import com.example.chat.model.User;
import com.example.chat.repository.UserRepository;
import com.example.chat.sequrity.UserDetailsImpl;
import com.example.chat.utils.exception.UserAccountNotActivatedException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, UserAccountNotActivatedException {
        Optional<User> userOptional = userRepository.findByUserLogin(login);
        if (login.contains("@")) {
            userOptional = userRepository.findByEmail(login);
        }
        if (userOptional.isEmpty()) {
            throw new BadCredentialsException("user.bad.authorisation");
        }
        if(!userOptional.get().isEnable()) throw new UserAccountNotActivatedException("email.not.verification");
        User user = userOptional.get();
        return new UserDetailsImpl(user);
    }
}
