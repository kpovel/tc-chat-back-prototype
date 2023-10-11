package com.example.demo.payload.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

  @NotBlank(message = "Поле login не може бути пустим")
  @Size(min = 3, message = "Мінімальна довжина поля login 3 символи")
  @Size(max = 51, message = "Максимальна довжина поля login 50 символів")
  @Pattern(regexp = "^\\S*$", message = "Поле login не може мати пропуски")
  @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Поле login може мати тільки латинські букви та цифри")
  private String login;

  @NotBlank(message = "Поле email не може бути пустим")
  @Size(max = 51, message = "Максимальна довжина поля email 50 символів")
  @Email(message = "Повинна бути правильно сформована адреса електронної пошти")
  private String email;

  @NotBlank(message = "Поле password не може бути пустим")
  @Size(min = 6, message = "Мінімальна довжина поля password 6 символів")
  @Size(max = 73, message = "Максимальна довжина поля password 72 символи")
  @Pattern(regexp = "^\\S*$", message = "Поле password не може мати пропуски")
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Створіть будь ласка більш надійний пароль")
  private String password;

}
