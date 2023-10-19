package com.example.demo.controller;

import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.servise.UserService;
import com.example.demo.validate.CustomFieldError;
import com.example.demo.validate.ValidateUserField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "User rest controller", description = "Rest controller user account")
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ValidateUserField validate;
    private final MessageSource messageSource;

    @PostMapping("/signup")
    @Operation(summary = "Registration new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = CustomFieldError.class), mediaType = "application/json") })
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,
                                          BindingResult bindingResult, @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag("en"));
        if (acceptLanguage != null && acceptLanguage.equals("uk-UA")) LocaleContextHolder.setLocale(Locale.forLanguageTag("uk"));
        Locale currentLocale = LocaleContextHolder.getLocale();
        if (bindingResult.hasErrors()) {
            try {
                List<CustomFieldError> errorFields = bindingResult.getFieldErrors().stream()
                        .map(fieldError -> new CustomFieldError(fieldError.getField(), messageSource.getMessage(fieldError.getDefaultMessage(), null, currentLocale)))
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorFields);
            } catch (NoSuchMessageException e) {
                return ResponseEntity.badRequest().body(new ArrayList<>(List.of(
                        new CustomFieldError("serverError", messageSource.getMessage("server.error", null, currentLocale)))));
            }
        }
        if (validate.existsByLogin(signUpRequest.getLogin())) {
            return ResponseEntity.badRequest().body(new ArrayList<>(List.of(
                    new CustomFieldError("loginDuplicate", messageSource.getMessage("login.duplicate", null, currentLocale)))));
        }
        if (validate.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ArrayList<>(List.of(
                    new CustomFieldError("emailDuplicate", messageSource.getMessage("email.duplicate", null, currentLocale)))));
        }
        userService.createUser(signUpRequest);
        return ResponseEntity.ok(messageSource.getMessage("user.signup.success", null, currentLocale));
    }


    @GetMapping("/verification-user-email/{code}")
    @Operation(summary = "Verification user email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class)) })
    })
    public ResponseEntity<?> verificationUserEmail(@PathVariable("code") String code) {
        boolean statusActivation = userService.verificationUserEmail(code);
        Locale currentLocale = LocaleContextHolder.getLocale();
        if(statusActivation){
            return ResponseEntity.ok(messageSource.getMessage("user.success.enable.account", null, currentLocale));
        } else {
            return ResponseEntity.badRequest().body(messageSource.getMessage("user.bad.enable.account", null, currentLocale));
        }
    }

    @PutMapping("/forgot-password")
    @Operation(summary = "Forgot password, step one(-TODO-)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class)) })
    })
    public ResponseEntity<?> forgotUserPasswordOneStep(@RequestParam String userEmail) {
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


    @Operation(summary = "Test protected endpoint")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/chat")
    public ResponseEntity<String> securityPage() {
        return ResponseEntity.ok("It is Chat page - protected");
    }

}
