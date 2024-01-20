package com.example.chat.servise;

import com.example.chat.model.User;
import com.example.chat.payload.request.*;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserService {

    void createUser(SignupRequest signUpRequest, String XOriginatingHost) throws MessagingException;

    Optional<User> verificationUserEmail(String code);

    Authentication userAuthentication(User user);

    void forgotPasswordStepOne(UserEmailRequest userEmail) throws MessagingException;
    Optional<User> forgotPasswordStepTwo(String codeFromEmail) throws MessagingException;

    User getUserFromSecurityContextHolder();

    /**
     * Finds a user in the database by checking the given user ID and converts it into DTO.
     *
     * @param id ID of the user.
     * @return Optional containing the converted user as the result, else - InvalidDataException.
     */

    User getUserById(long id);

    boolean isOldUserPassword(User user,UserPasswordRequest userPassword);

    void saveNewUserPassword(User user, UserPasswordRequest userPassword);

    void saveUserHashtagsWithOnboarding(List<HashtagRequest> hashtags);

    void saveUserAboutWithOnboarding(String userAbout);

    void saveDefaultAvatarWithOnboarding(String defaultAvatarName);

    void userOnboardingEnd(UserOnboardingSteps onboardingEnd);
}
