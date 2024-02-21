package com.example.chat.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
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
    @Size(max = 72, message = "password.maxsize")
    private String password;

    public LoginRequest() {
    }
}
