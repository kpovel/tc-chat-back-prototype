package com.example.chat.controller;

import com.example.chat.model.Hashtag;
import com.example.chat.model.User;
import com.example.chat.payload.request.HashtagRequest;
import com.example.chat.utils.Views;
import com.example.chat.payload.request.SignupRequest;
import com.example.chat.payload.response.JwtResponse;
import com.example.chat.payload.response.ParserToResponseFromCustomFieldError;
import com.example.chat.sequrity.jwt.JwtUtils;
import com.example.chat.servise.AuthService;
import com.example.chat.servise.UserService;
import com.example.chat.validate.CustomFieldError;
import com.example.chat.validate.ValidateUserField;
import com.example.chat.web.dto.UserDto;
import com.example.chat.web.mapper.HashtagMapper;
import com.example.chat.web.mapper.UserMapper;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    private final UserService userService;
    private final ValidateUserField validate;
    private final MessageSource messageSource;

    private final AuthService authService;

    private final JwtUtils jwtUtils;

   private final UserMapper userMapper;
    private final HashtagMapper hashtagMapper;

    @PostMapping("/signup")
    @Operation(summary = "Registration new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ParserToResponseFromCustomFieldError.class), mediaType = "application/json") })
    })

    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,
                                          BindingResult bindingResult,
                                          @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage,
                                          @RequestHeader(value = "X-Originating-Host", required = false) String XOriginatingHost) throws MessagingException {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("en"));
        if (acceptLanguage != null && acceptLanguage.equals("uk")) LocaleContextHolder.setLocale(Locale.forLanguageTag("uk"));
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
        if (validate.existsByLogin(signUpRequest.getLogin())) {
            return ResponseEntity.badRequest().body(new CustomFieldError("loginDuplicate", messageSource.getMessage("login.duplicate", null, currentLocale)));
        }
        if (validate.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new CustomFieldError("emailDuplicate", messageSource.getMessage("email.duplicate", null, currentLocale)));
        }
        userService.createUser(signUpRequest, XOriginatingHost);
        return ResponseEntity.ok(messageSource.getMessage("user.signup.success", null, currentLocale));
    }


    @PutMapping("/validate-email/{code}")
    @Operation(summary = "Verification user email and authentication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = JwtResponse.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class)) })
    })
    public ResponseEntity<?> verificationUserEmail(@PathVariable("code") String code,
                                                   @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("en"));
        if (acceptLanguage != null && acceptLanguage.equals("uk")) LocaleContextHolder.setLocale(Locale.forLanguageTag("uk"));
        Locale currentLocale = LocaleContextHolder.getLocale();
        Optional<User> optionalUser = userService.verificationUserEmail(code);
        if(optionalUser.isPresent()){
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
    @Operation(summary = "Forgot password, step one (-TODO-)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class)) })
    })
    public ResponseEntity<?> forgotUserPasswordOneStep(@RequestParam String userEmail) throws MessagingException {
        userService.forgotPasswordOneStep(userEmail);
        //TODO
        return null;
    }
    @PutMapping("/forgot-password/{code}")
    @Operation(summary = "Forgot password, step two (-TODO-)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class)) })
    })
    public ResponseEntity<?> forgotUserPasswordTwoStep(@PathVariable("code")String code) {

        //TODO
        return null;
    }

    @GetMapping("/user")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get User (-TODO-)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class)) })
    })
    @JsonView(Views.ViewFieldUser.class)
    public ResponseEntity<UserDto> getUserToProfile(){
        User user = userService.getUserById(1L);
        UserDto dto = userMapper.toDto(user);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "User onboarding - save hashtags")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/user/hashtags-with-onboarding/save")
    public ResponseEntity<?> saveUserHashtagsWithOnboarding(@RequestBody List<HashtagRequest> hashtags) {
        if(hashtags == null) throw new NullPointerException("hashtags is NULL");
        userService.saveUserHashtagsWithOnboarding(hashtags);
        return ResponseEntity.ok("Ok");
    }

    @Operation(summary = "User onboarding - save user about field")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/user/user-about-with-onboarding/save")
    public ResponseEntity<?> saveUserAboutFieldOnboarding(@RequestParam(value = "userAbout") String userAbout) {
        if(userAbout == null) throw new NullPointerException("userAbout is NULL");
        //TODO
        userService.saveUserAboutWithOnboarding(userAbout);
        return ResponseEntity.ok("Ok");
    }

//    @Operation(summary = "Test protected endpoint")
//    @SecurityRequirement(name = "Bearer Authentication")
//    @GetMapping("/chat")
//    public ResponseEntity<String> securityPage() {
//        return ResponseEntity.ok("It is Chat page - protected");
//    }

}
