package com.example.demo.payload.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {
//  @NotBlank
//  @Size(min = 3, max = 20)
//  private String name;

//  @NotBlank
//  @Size(min = 3, max = 20)
  private String userLogin;

//  @NotBlank
//  @Size(max = 50)
//  @Email
  private String email;

//  @NotBlank
//  @Size(min = 6, max = 40)
  private String password;

}
