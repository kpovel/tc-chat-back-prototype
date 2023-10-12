package com.example.demo.payload.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

//  @NotBlank(message = "Поле login не може бути пустим")
  @NotBlank(message = "login.notblank")
//  @Size(min = 3, message = "Мінімальна довжина поля login 3 символи")
  @Size(min = 3, message = "login.minsize")
//  @Size(max = 50, message = "Максимальна довжина поля login 50 символів")
  @Size(max = 50, message = "login.maxsize")
//  @Pattern(regexp = "^\\S*$", message = "Поле login не може мати пропуски")
  @Pattern(regexp = "^\\S*$", message = "login.spaces")
//  @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Поле login може мати тільки латинські букви та цифри")
  @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "login.pattern")
  private String login;

//  @NotBlank(message = "Поле email не може бути пустим")
  @NotBlank(message = "email.notblank")
//  @Size(max = 50, message = "Максимальна довжина поля email 50 символів")
  @Size(max = 50, message = "email.maxsize")
//  @Email(message = "Повинна бути правильно сформована адреса електронної пошти")
  @Email(message = "email.email")
  private String email;

//  @NotBlank(message = "Поле password не може бути пустим")
  @NotBlank(message = "password.notblank")
//  @Size(min = 6, message = "Мінімальна довжина поля password 6 символів")
  @Size(min = 6, message = "password.minsize")
//  @Size(max = 72, message = "Максимальна довжина поля password 72 символи")
  @Size(max = 72, message = "password.maxsize")
//  @Pattern(regexp = "^\\S*$", message = "Поле password не може мати пропуски")
  @Pattern(regexp = "^\\S*$", message = "password.spaces")
//  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Створіть будь ласка більш надійний пароль")
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "{password.pattern}")
  private String password;

}
