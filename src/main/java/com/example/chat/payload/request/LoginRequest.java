package com.example.chat.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotNull( message = "login.minsize")
    @Size(min = 3, message = "login.minsize")
    @Size(max = 50, message = "login.maxsize")
    private String login;

    @NotBlank(message = "password.minsize")
    @Size(min = 6, message = "password.minsize")
    @Size(max = 50, message = "password.maxsize")
    private String password;

    public LoginRequest() {
    }
}
