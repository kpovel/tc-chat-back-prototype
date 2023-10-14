package com.example.demo.payload.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {
  @Size(min = 3, message = "login.minsize")
  @Size(max = 50, message = "login.maxsize")
  @Pattern(regexp = "^\\S*$", message = "login.spaces")
  @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "login.pattern")
  private String login;

  @NotBlank(message = "email.notblank")
  @Size(max = 50, message = "email.maxsize")
  @Email(message = "email.email")
  private String email;

  @Size(min = 6, message = "password.minsize")
  @Size(max = 72, message = "password.maxsize")
  @Pattern(regexp = "^\\S*$", message = "password.spaces")
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$^#!%*?&()])[A-Za-z\\d@$^#!%*?&()]+$", message = "password.pattern")
  private String password;

}
