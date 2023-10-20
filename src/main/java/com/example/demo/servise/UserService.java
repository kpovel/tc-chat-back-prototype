package com.example.demo.servise;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
//@Slf4j
public class UserService {
    @Value(("${front.host}"))
    private String host;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailSenderService mailSenderService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private MessageSource messageSource;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       MailSenderService mailSenderService,
                       UserDetailsServiceImpl userDetailsServiceImpl,
                       MessageSource messageSource) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.messageSource = messageSource;
    }


    public void createUser(SignupRequest signUpRequest) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String email = signUpRequest.getEmail();
        User user = new User();
        user.setEmail(email);
        user.setLocale(currentLocale.getLanguage());
        user.setUserLogin(signUpRequest.getLogin());
        user.setName(signUpRequest.getLogin());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.getAuthority().add(Role.ROLE_USER);
        user.setEnable(false);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        if (!StringUtils.isEmpty(user.getEmail())) {
            String mailText = String.format(messageSource.getMessage("mail.activation.code", null, currentLocale),
                    user.getUserLogin(), host + "/" + user.getLocale(), user.getActivationCode());
            mailSenderService.sendSimpleMessage(user.getEmail(), messageSource.getMessage("mail.subject.activation", null, currentLocale), mailText);
        }
    }


    public Optional<User> verificationUserEmail(String code) {
        Optional<User> userOptional = userRepository.findByActivationCode(code);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setEnable(true);
            user.setActivationCode(null);
            userRepository.save(user);
            return Optional.of(user);
        }
        return userOptional;
    }

    public Authentication userAuthentication(User user) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getEmail());;
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public void forgotPasswordOneStep(String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Locale currentLocale = Locale.forLanguageTag(user.getLocale());
            user.setActivationCode(UUID.randomUUID().toString());


            String mailText = String.format(messageSource.getMessage("mail.forgot.password.step.one", null, currentLocale),
                    user.getUserLogin(), host, user.getActivationCode());
            mailSenderService.sendSimpleMessage(user.getEmail(), messageSource.getMessage("mail.subject.forgot.password.step.one", null, currentLocale), mailText);

        } else {

        }

    }
}
