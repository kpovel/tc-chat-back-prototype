package com.example.chat.servise.impls;

import com.example.chat.model.*;
import com.example.chat.payload.request.*;
import com.example.chat.repository.UserRepository;
import com.example.chat.sequrity.UserDetailsImpl;
import com.example.chat.servise.UserService;
import com.example.chat.utils.exception.InvalidDataException;
import com.example.chat.utils.mapper.HashtagMapper;
import jakarta.mail.MessagingException;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import java.util.*;

//@Slf4j
@Service
@Data
public class UserServiceImpl implements UserService {

    @Value("${front.host}")
    @Transient
    private String host;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailSenderService mailSenderService;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final MessageSource messageSource;

    private final HashtagMapper hashtagMapper;

    private final MessageService messageService;

    @Override
    public void createUser(SignupRequest signUpRequest, String XOriginatingHost) throws MessagingException {
        if (XOriginatingHost != null) host = XOriginatingHost;
        Locale currentLocale = LocaleContextHolder.getLocale();
        String email = signUpRequest.getEmail();
        User user = new User();
        user.setEmail(email);
        user.setUserLogin(signUpRequest.getLogin());
        user.setName(signUpRequest.getLogin());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.getAuthority().add(Role.ROLE_USER);
        user.setUniqueServiceCode(UUID.randomUUID().toString());
        Image defaultAvatar = new Image();
        defaultAvatar.setName("no-avatar.svg");
        user.setImage(defaultAvatar);
        userRepository.save(user);
        if (!StringUtils.isEmpty(user.getEmail())) {
            Context context = new Context();
            context.setVariable("username", user.getName());
            context.setVariable("host", host + "/" + currentLocale.getLanguage());
            context.setVariable("code", user.getUniqueServiceCode());
            mailSenderService.sendSimpleMessage(user.getEmail(), messageSource.getMessage("mail.subject.activation", null, currentLocale), "activation_message_" + currentLocale.getLanguage(), context);
        }
    }

    @Override
    public Optional<User> verificationUserEmail(String code) {
        Optional<User> userOptional = userRepository.findUsersByUniqueServiceCode(code);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEnable(true);
            user.setUniqueServiceCode(null);
            userRepository.save(user);
            return Optional.of(user);
        }
        return userOptional;
    }

    @Override
    public Authentication userAuthentication(User user) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public void forgotPasswordStepOne(UserEmailRequest userEmail, String XOriginatingHost) throws MessagingException {
        if (XOriginatingHost != null) host = XOriginatingHost;
        Optional<User> userOptional = userRepository.findByEmail(userEmail.getUserEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Locale currentLocale = LocaleContextHolder.getLocale();
            user.setUniqueServiceCode(UUID.randomUUID().toString());
            userRepository.save(user);
            Context context = new Context();
            context.setVariable("username", user.getName());
            context.setVariable("host", host + "/" + currentLocale.getLanguage());
            context.setVariable("code", user.getUniqueServiceCode());
            mailSenderService.sendSimpleMessage(user.getEmail(), messageSource.getMessage("mail.subject.forgot.password.step.one", null, currentLocale), "forgot_password_message_" + currentLocale.getLanguage(), context);
        } else {
            throw new BadCredentialsException("user.bad.email.forgot.password");
        }
    }

    @Override
    public Optional<User> forgotPasswordStepTwo(String codeFromEmail) {
        Optional<User> userOptional = userRepository.findUsersByUniqueServiceCode(codeFromEmail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUniqueServiceCode(null);
            user.setEnable(true);
            user.setOnboardingEnd(true);
            userRepository.save(user);
        }
        return userOptional;
    }

    @Override
    public User getUserFromSecurityContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername()).get();
    }

    @Override
    @Transactional
    public User getUserById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) return userOptional.get();
        throw new InvalidDataException("The user with id " + id + " is missing");
    }

    @Override
    public User getUserByUUID(String userUUID) {
        Optional<User> userOptional = userRepository.findUserByUuid(userUUID);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        //TODO: Edit message Exception!!!!
        else throw new BadCredentialsException("user.bad.email.forgot.password");
    }


    @Override
    public boolean isOldUserPassword(User user, String oldUserPassword) {
        String encodedPassword = user.getPassword();
        return passwordEncoder.matches(oldUserPassword, encodedPassword);
    }

    @Override
    public void saveNewUserPassword(User user, String newUserPassword) {
        user.setPassword(passwordEncoder.encode(newUserPassword));
        userRepository.save(user);
    }

    @Override
    public void saveUserHashtagsWithOnboarding(List<HashtagRequest> hashtags) {
        User user = getUserFromSecurityContextHolder();
        if (user.isOnboardingEnd()) throw new InvalidDataException("User onboarding is END!");
        for (HashtagRequest arr : hashtags) {
            Hashtag hashtag = hashtagMapper.toModel(arr);
            user.getHashtags().add(hashtag);
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveUserHashtags(List<HashtagRequest> hashtags) {
        User user = getUserFromSecurityContextHolder();
        user.getHashtags().clear();
        for (HashtagRequest arr : hashtags) {
            Hashtag hashtag = hashtagMapper.toModel(arr);
            user.getHashtags().add(hashtag);
        }
        userRepository.save(user);
    }

    @Override
    public void saveUserAboutWithOnboarding(String userAbout) {
        User user = getUserFromSecurityContextHolder();
        if (user.isOnboardingEnd()) throw new InvalidDataException("User onboarding is END!");
        user.setAbout(userAbout);
        userRepository.save(user);
    }

    @Override
    public void saveDefaultAvatarWithOnboarding(String defaultAvatarName) {
        User user = getUserFromSecurityContextHolder();
        if (user.isOnboardingEnd()) throw new InvalidDataException("User onboarding is END!");

        user.getImage().setName(defaultAvatarName);
        userRepository.save(user);
    }

    @Override
    public void userOnboardingEnd(UserOnboardingSteps onboardingEnd) {
        User user = getUserFromSecurityContextHolder();
        if (user.isOnboardingEnd()) throw new InvalidDataException("User onboarding is END!");
        user.setOnboardingEnd(onboardingEnd.isOnboardingEnd());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public List<UserChatRoom> getUserChatRoomList() {
        User user = getUserFromSecurityContextHolder();
        List<UserChatRoom> userChatRooms = user.getUserChatRooms();
        for (int i = 0; i < userChatRooms.size(); i++) {
            if(userChatRooms.get(i).getChatRoom().getUserAminChatRoom().getId().equals(user.getId())) {
                userChatRooms.get(i).setAdmin(true);
            }
        }
        if (!userChatRooms.isEmpty()) {
            return addLastMessageAndChatNameToListUserChatRoomsResponse(userChatRooms);
        }
        return userChatRooms;
    }

    private List<UserChatRoom> addLastMessageAndChatNameToListUserChatRoomsResponse(List<UserChatRoom> userChatRooms) {
        for (int i = 0; i < userChatRooms.size(); i++) {
            List<ChatRoomType> chatRoomType = List.copyOf(userChatRooms.get(i).getChatRoom().getChatRoomType());
            if (chatRoomType.get(0).equals(ChatRoomType.PUBLIC)) {
                userChatRooms.get(i).setChatName(userChatRooms.get(i).getChatRoom().getName());
            }
            userChatRooms.get(i).setLastMessage(
                    messageService.getLastMessageByChatRoom(userChatRooms.get(i).getId()).orElse(null)
            );
        }
        return userChatRooms;
    }

    @Override
    @Transient
    public void addPublicChatRoomToUserChatRoomsSet(ChatRoom chatRoom) {
        User user = getUserFromSecurityContextHolder();
        List<UserChatRoom> userChatRooms = user.getUserChatRooms();
        for (UserChatRoom arr : userChatRooms) {
            if (arr.getChatRoom().getId().equals(chatRoom.getId())) throw new InvalidDataException(" ");
        }

        UserChatRoom userChatRoom = new UserChatRoom();
        userChatRoom.setChatRoom(chatRoom);
        userChatRoom.setUser(user);
        user.getUserChatRooms().add(userChatRoom);
        userRepository.save(user);

    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public void saveUserFields(User user, UserEditRequest userRequest) {
        user.setName(userRequest.getName());
        user.setAbout(userRequest.getAbout());
        userRepository.save(user);
    }
}
