package com.example.chat.controller;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.User;
import com.example.chat.model.UserChatRoom;
import com.example.chat.payload.request.*;
import com.example.chat.payload.response.JwtResponse;
import com.example.chat.payload.response.ParserToResponseFromCustomFieldError;
import com.example.chat.sequrity.jwt.JwtUtils;
import com.example.chat.servise.impls.AuthService;
import com.example.chat.servise.impls.ChatRoomService;
import com.example.chat.servise.impls.FileService;
import com.example.chat.servise.impls.UserServiceImpl;
import com.example.chat.utils.CustomFieldError;
import com.example.chat.utils.JsonViews;
import com.example.chat.utils.exception.BadRefreshTokenException;
import com.example.chat.utils.exception.InvalidDataException;
import com.example.chat.utils.mapper.HashtagMapper;
import com.example.chat.utils.validate.ValidateFields;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "User rest controller", description = "Rest controller user account")
@RestController
@RequestMapping("api")
@Data
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class UserController {

    private final UserServiceImpl userService;

    private final ValidateFields validate;

    private final MessageSource messageSource;

    private final AuthService authService;

    private final JwtUtils jwtUtils;

    private final HashtagMapper hashtagMapper;

    private final FileService fileService;

    private final ChatRoomService chatRoomService;

    @PostMapping("/signup")
    @Operation(summary = "Registration new user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,
                                          BindingResult bindingResult,
                                          @RequestHeader(value = "X-Originating-Host", required = false) String XOriginatingHost) throws MessagingException {

        Locale currentLocale = LocaleContextHolder.getLocale();
        List<CustomFieldError> errorFields = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            try {
                errorFields = bindingResult.getFieldErrors().stream()
                        .map(fieldError -> new CustomFieldError(fieldError.getField(), messageSource.getMessage(fieldError.getDefaultMessage(), null, currentLocale)))
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(ParserToResponseFromCustomFieldError.parseCustomFieldErrors(errorFields));
            } catch (NoSuchMessageException e) {
                errorFields.clear();
                errorFields.add(new CustomFieldError("serverError", messageSource.getMessage("server.error", null, currentLocale)));
                return ResponseEntity.badRequest().body(ParserToResponseFromCustomFieldError.parseCustomFieldErrors(errorFields));
            }
        }
        if (validate.existsByLogin(signUpRequest.getLogin())) {
            errorFields.add(new CustomFieldError("login", messageSource.getMessage("login.duplicate", null, currentLocale)));
            return ResponseEntity.badRequest().body(ParserToResponseFromCustomFieldError.parseCustomFieldErrors(errorFields));
        }
        if (validate.existsByEmail(signUpRequest.getEmail())) {
            errorFields.add(new CustomFieldError("email", messageSource.getMessage("email.duplicate", null, currentLocale)));
            return ResponseEntity.badRequest().body(ParserToResponseFromCustomFieldError.parseCustomFieldErrors(errorFields));
        }
        userService.createUser(signUpRequest, XOriginatingHost);
        return ResponseEntity.ok(messageSource.getMessage("user.signup.success", null, currentLocale));
    }


    @PutMapping("/validate-email/{code}")
    @Operation(summary = "Verification user email and authentication")
    public ResponseEntity<?> verificationUserEmail(@PathVariable("code") String code) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        Optional<User> optionalUser = userService.verificationUserEmail(code);
        if (optionalUser.isPresent()) {
            Authentication authentication = userService.userAuthentication(optionalUser.get());

            final String jwtAccessToken = jwtUtils.generateJwtAccessToken(authentication);
            final String jwtRefreshToken = jwtUtils.generateRefreshToken(authentication);
            authService.saveJwtRefreshTokenToStorage(optionalUser.get().getEmail(), jwtRefreshToken);
            return ResponseEntity.ok(new JwtResponse(jwtAccessToken, jwtRefreshToken));
        } else {
            return ResponseEntity.badRequest().body(messageSource.getMessage("user.bad.enable.account", null, currentLocale));
        }
    }


    @PutMapping("/forgot-password")
    @Operation(summary = "Forgot password, step one")
    public ResponseEntity<?> forgotUserPasswordOneStep(@Valid @RequestBody UserEmailRequest userEmail,
                                                       BindingResult bindingResult,
                                                       @RequestHeader(value = "X-Originating-Host", required = false) String xOriginatingHost) throws MessagingException {
        Locale currentLocale = LocaleContextHolder.getLocale();
        if (bindingResult.hasErrors()) {
            try {
                List<CustomFieldError> errorFields = bindingResult.getFieldErrors().stream()
                        .map(fieldError -> new CustomFieldError(fieldError.getField(), messageSource.getMessage(fieldError.getDefaultMessage(), null, currentLocale)))
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(ParserToResponseFromCustomFieldError.parseCustomFieldErrors(errorFields));
            } catch (NoSuchMessageException e) {
                return ResponseEntity.badRequest().body(new CustomFieldError("serverError", messageSource.getMessage("server.error", null, currentLocale)));
            }
        }
        userService.forgotPasswordStepOne(userEmail, xOriginatingHost);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/forgot-password/{code}")
    @Operation(summary = "Forgot password, step two")
    public ResponseEntity<?> forgotUserPasswordTwoStep(@PathVariable String code) {

        Locale currentLocale = LocaleContextHolder.getLocale();
        Optional<User> userOptional = userService.forgotPasswordStepTwo(code);
        if (userOptional.isPresent()) {
            Authentication authentication = userService.userAuthentication(userOptional.get());

            final String jwtAccessToken = jwtUtils.generateJwtAccessToken(authentication);
            final String jwtRefreshToken = jwtUtils.generateRefreshToken(authentication);
            authService.saveJwtRefreshTokenToStorage(userOptional.get().getEmail(), jwtRefreshToken);
            return ResponseEntity.ok(new JwtResponse(jwtAccessToken, jwtRefreshToken));
        } else {
            return ResponseEntity.badRequest().body(messageSource.getMessage("user.bad.code.forgot.password", null, currentLocale));
        }
    }

    @PutMapping("/user/new-password/save")
    @Operation(summary = "Save new User password")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> saveNewUserPassword(@Valid @RequestBody UserPasswordRequest newUserPassword,
                                                 BindingResult bindingResult) {

        Locale currentLocale = LocaleContextHolder.getLocale();
        User user = userService.getUserFromSecurityContextHolder();
        if (bindingResult.hasErrors()) {
            List<CustomFieldError> errorFields = new ArrayList<>();
            try {
                errorFields = bindingResult.getFieldErrors().stream()
                        .map(fieldError -> new CustomFieldError(fieldError.getField(), messageSource.getMessage(fieldError.getDefaultMessage(), null, currentLocale)))
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(ParserToResponseFromCustomFieldError.parseCustomFieldErrors(errorFields));
            } catch (NoSuchMessageException e) {
                errorFields.add(new CustomFieldError("serverError", messageSource.getMessage("server.error", null, currentLocale)));
                return ResponseEntity.badRequest().body(ParserToResponseFromCustomFieldError.parseCustomFieldErrors(errorFields));
            }
        }
        if (!userService.isOldUserPassword(user, newUserPassword.getUserPassword())) {
            userService.saveNewUserPassword(user, newUserPassword.getUserPassword());
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.badRequest().body(messageSource.getMessage("user.bad.new.password", null, currentLocale));
    }

    @GetMapping("/user")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get User (-TODO-)")
    @JsonView(JsonViews.ViewFieldUu.class)
    public ResponseEntity<?> getUserToProfile() {
        User user = userService.getUserById(1L);
//        UserDto dto = userMapper.toDto(user);
        return ResponseEntity.ok("dto");
    }

    @PutMapping("/user/hashtags-with-onboarding/save")
    @Operation(summary = "User onboarding (step: 'HASHTAGS') - save hashtags")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> saveUserHashtagsWithOnboarding(@RequestBody List<HashtagRequest> hashtags) {
        if (hashtags == null) throw new NullPointerException("response - hashtags is NULL");
        userService.saveUserHashtagsWithOnboarding(hashtags);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/user/user-about-with-onboarding/save")
    @Operation(summary = "User onboarding (step: 'ABOUT') - save user about field")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> saveUserAboutFieldOnboarding(@RequestBody UserOnboardingSteps userAbout) {
        String userAboutStr = userAbout.getOnboardingFieldStr();
        if (userAboutStr == null) throw new NullPointerException("response - user about field is NULL");
        if (userAboutStr.length() > 300) throw new InvalidDataException("userAbout field max size 300 characters");
        userService.saveUserAboutWithOnboarding(userAboutStr);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/user/default-avatar-with-onboarding/save")
    @Operation(summary = "User onboarding (step: 'AVATAR')- save default avatar")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> userHasChosenDefaultAvatar(@RequestBody UserOnboardingSteps nameDefaultAvatar) {
        String nameAvatar = nameDefaultAvatar.getOnboardingFieldStr();
        if (nameAvatar == null && fileService.defaultImage(nameAvatar))
            throw new NullPointerException("response - name avatar is NULL or bad name avatar");
        if (!fileService.defaultImage(nameDefaultAvatar.getOnboardingFieldStr()))
            throw new InvalidDataException("Bad name image");
        userService.saveDefaultAvatarWithOnboarding(nameAvatar);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/user/onboarding/get-user")
    @Operation(summary = "User onboarding (step: 'START') - get user")
    @SecurityRequirement(name = "Bearer Authentication")
    @JsonView(JsonViews.ViewFieldUserOnboarding.class)
    public ResponseEntity<User> onboardingGetUser() {
        return ResponseEntity.ok(userService.getUserFromSecurityContextHolder());
    }

    @PutMapping("/user/onboarding/end")
    @Operation(summary = "User onboarding (step: 'START') - end onboarding, set onboardingEnd field - 'true'")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> onboardingEnd(@RequestBody UserOnboardingSteps onboardingEnd) {
        if (!onboardingEnd.isOnboardingEnd()) throw new InvalidDataException("'true' - is preferred");
        userService.userOnboardingEnd(onboardingEnd);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/user/chat-rooms")
    @Operation(summary = "Get user chat rooms")
    @SecurityRequirement(name = "Bearer Authentication")
    @JsonView(JsonViews.ViewFieldUUIDChatList.class)
    public ResponseEntity<List<UserChatRoom>> getUserChatRooms() {
        //TODO: Add a filter by the date of the last message of the chat message.
        return ResponseEntity.ok(userService.getUserChatRooms());
    }

    @PutMapping("/user/add-public-chatroom/{chatRoomUUID}")
    @Operation(summary = "Add user in public chat rooms")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> addPublicChatRoomToUserChatRoomsSet(@PathVariable String chatRoomUUID) {
        ChatRoom chatRoom = chatRoomService.getChatRoomByUUID(chatRoomUUID);
        userService.addPublicChatRoomToUserChatRoomsSet(chatRoom);
        return ResponseEntity.ok("Success");
    }


    @Operation(summary = "Delete user")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser() throws BadRefreshTokenException {
        User user = userService.getUserFromSecurityContextHolder();
        if (authService.userSearchByRefreshStorage(user.getEmail())) {
            authService.userDeleteByRefreshStorage(user.getEmail());
            userService.deleteUser(user);
            return ResponseEntity.ok("Success");
        } else throw new BadRefreshTokenException(" ");
    }

    @PutMapping("/user/edit-password")
    @Operation(summary = "User edit password")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> editUserPassword(@Valid @RequestBody EditUserPassword editUserPassword,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResultMessages(bindingResult));
        }

        User user = userService.getUserFromSecurityContextHolder();

        if (!userService.isOldUserPassword(user, editUserPassword.getOldPassword())) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            CustomFieldError messageError = new CustomFieldError("oldPassword", messageSource.getMessage("user.bad.old.password", null, currentLocale));
            return ResponseEntity.badRequest().body(ParserToResponseFromCustomFieldError.parseCustomFieldError(messageError));
        }

        userService.saveNewUserPassword(user, editUserPassword.getNewPassword());
        return ResponseEntity.ok("Success");
    }


    private Map<String, String> bindingResultMessages(BindingResult bindingResult) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        List<CustomFieldError> errorFields = new ArrayList<>();
        try {
            errorFields = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> new CustomFieldError(fieldError.getField(), messageSource.getMessage(fieldError.getDefaultMessage(), null, currentLocale)))
                    .collect(Collectors.toList());
            return ParserToResponseFromCustomFieldError.parseCustomFieldErrors(errorFields);
        } catch (NoSuchMessageException e) {
            errorFields.clear();
            errorFields.add(new CustomFieldError("serverError", messageSource.getMessage("server.error", null, currentLocale)));
            return ParserToResponseFromCustomFieldError.parseCustomFieldErrors(errorFields);
        }
    }

}
