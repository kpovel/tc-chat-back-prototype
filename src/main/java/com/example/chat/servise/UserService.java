package com.example.chat.servise;

import com.example.chat.model.Image;
import com.example.chat.payload.request.SignupRequest;
import com.example.chat.repository.UserRepository;
import com.example.chat.model.Role;
import com.example.chat.model.User;
import com.example.chat.sequrity.UserDetailsImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

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


    public void createUser(SignupRequest signUpRequest, String XOriginatingHost) throws MessagingException {
        if(XOriginatingHost != null) host = XOriginatingHost;
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
        Image defaultAvatar = new Image();
        defaultAvatar.setName("no-avatar.jpg");
        user.setImage(defaultAvatar);
        userRepository.save(user);
        if (!StringUtils.isEmpty(user.getEmail())) {
            Context context = new Context();
            context.setVariable("username", user.getName());
            context.setVariable("host", host);
            context.setVariable("code", user.getActivationCode());
            mailSenderService.sendSimpleMessage(user.getEmail(), messageSource.getMessage("mail.subject.activation", null, currentLocale), "activation_message_" + currentLocale.getLanguage(), context);
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

    public void forgotPasswordOneStep(String userEmail) throws MessagingException {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Locale currentLocale = Locale.forLanguageTag(user.getLocale());
            user.setActivationCode(UUID.randomUUID().toString());
            Context context = new Context();
            context.setVariable("username", user.getName());
            context.setVariable("host", host);
            context.setVariable("code", user.getActivationCode());
            mailSenderService.sendSimpleMessage(user.getEmail(), messageSource.getMessage("mail.subject.forgot.password.step.one", null, currentLocale), "forgot_password_message_" + currentLocale.getLanguage(), context);

        } else {

        }
    }

    public User getUserFromSecurityContextHolder() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return userRepository.findByEmail(userDetails.getUsername()).get();
    }

    //???????????????????
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }


    // Перевірка чи не прислав юзер новий пароль при зміні таким же як старий
    public void notIsOldPassword(String password){

    }

}
