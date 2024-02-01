package com.example.chat.controller;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.User;
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
import com.example.chat.utils.dto.UserDto;
import com.example.chat.utils.exception.InvalidDataException;
import com.example.chat.utils.mapper.HashtagMapper;
import com.example.chat.utils.mapper.UserMapper;
import com.example.chat.utils.validate.ValidateFields;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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

    private final UserMapper userMapper;

    private final HashtagMapper hashtagMapper;

    private final FileService fileService;

    private final ChatRoomService chatRoomService;

    @PostMapping("/signup")
    @Operation(summary = "Registration new user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,
                                          BindingResult bindingResult,
                                          @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage,
                                          @RequestHeader(value = "X-Originating-Host", required = false) String XOriginatingHost) throws MessagingException {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("en"));
        if (acceptLanguage != null && acceptLanguage.equals("uk"))
            LocaleContextHolder.setLocale(Locale.forLanguageTag("uk"));
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
    public ResponseEntity<?> verificationUserEmail(@PathVariable("code") String code,
                                                   @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("en"));
        if (acceptLanguage != null && acceptLanguage.equals("uk"))
            LocaleContextHolder.setLocale(Locale.forLanguageTag("uk"));
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


    @PutMapping("/{lang}/forgot-password")
    @Operation(summary = "Forgot password, step one")
    public ResponseEntity<?> forgotUserPasswordOneStep(@Valid @RequestBody UserEmailRequest userEmail,
                                                       BindingResult bindingResult,
                                                       @PathVariable String lang) throws MessagingException {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("en"));
        if (lang.equals("uk")) LocaleContextHolder.setLocale(Locale.forLanguageTag("uk"));
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
        userService.forgotPasswordStepOne(userEmail);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/{lang}/forgot-password/{code}")
    @Operation(summary = "Forgot password, step two")
    public ResponseEntity<?> forgotUserPasswordTwoStep(@PathVariable String lang,
                                                       @PathVariable String code) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("en"));
        if (lang.equals("uk"))
            LocaleContextHolder.setLocale(Locale.forLanguageTag("uk"));
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
        User user = userService.getUserFromSecurityContextHolder();
        LocaleContextHolder.setLocale(Locale.forLanguageTag(user.getLocale()));
        Locale currentLocale = LocaleContextHolder.getLocale();
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
        if (!userService.isOldUserPassword(user, newUserPassword)) {
            userService.saveNewUserPassword(user, newUserPassword);
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.badRequest().body(messageSource.getMessage("user.bad.new.password", null, currentLocale));
    }

    @GetMapping("/user")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get User (-TODO-)")
    @JsonView(JsonViews.ViewFieldUu.class)
    public ResponseEntity<UserDto> getUserToProfile() {
        User user = userService.getUserById(1L);
        UserDto dto = userMapper.toDto(user);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/user/hashtags-with-onboarding/save")
    @Operation(summary = "User onboarding (step: 'HASHTAGS') - save hashtags")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> saveUserHashtagsWithOnboarding(@RequestBody List<HashtagRequest> hashtags) {
        if (hashtags == null) throw new NullPointerException("response - hashtags is NULL");
        userService.saveUserHashtagsWithOnboarding(hashtags);
        return ResponseEntity.ok("Ok");
    }

    @PutMapping("/user/user-about-with-onboarding/save")
    @Operation(summary = "User onboarding (step: 'ABOUT') - save user about field")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> saveUserAboutFieldOnboarding(@RequestBody UserOnboardingSteps userAbout) {
        String userAboutStr = userAbout.getOnboardingFieldStr();
        if (userAboutStr == null) throw new NullPointerException("response - user about field is NULL");
        if(userAboutStr.length() > 300 ) throw new InvalidDataException("userAbout field max size 300 characters");
        userService.saveUserAboutWithOnboarding(userAboutStr);
        return ResponseEntity.ok("Ok");
    }

    @PutMapping("/user/default-avatar-with-onboarding/save")
    @Operation(summary = "User onboarding (step: 'AVATAR')- save default avatar")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> userHasChosenDefaultAvatar(@RequestBody UserOnboardingSteps nameDefaultAvatar) {
        String nameAvatar = nameDefaultAvatar.getOnboardingFieldStr();
        if (nameAvatar == null && fileService.defaultImage(nameAvatar))
            throw new NullPointerException("response - name avatar is NULL or bad name avatar");
        userService.saveDefaultAvatarWithOnboarding(nameAvatar);
        return ResponseEntity.ok("Ok");
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
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/user/chat-rooms")
    @Operation(summary = "Get user chat rooms")
    @SecurityRequirement(name = "Bearer Authentication")
    @JsonView(JsonViews.ViewFieldUuChatList.class)
    public ResponseEntity<?> getUserChatRooms() {
        return ResponseEntity.ok(userService.getUserChatRooms());
    }

    @PutMapping("/user/add-public-chatroom/{chatRoomUUID}")
    @Operation(summary = "Add user in public chat rooms")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> addPublicChatRoomToUserChatRoomsSet(@PathVariable String chatRoomUUID) {
        ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomUUID);
        userService.addPublicChatRoomToUserChatRoomsSet(chatRoom);
        return ResponseEntity.ok("Ok");
    }


    @Operation(summary = "Delete user")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok("Ok");
    }


}
