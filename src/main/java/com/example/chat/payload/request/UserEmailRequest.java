package com.example.chat.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserEmailRequest {

    @NotBlank(message = "email.notblank")
    @Size(max = 50, message = "email.maxsize")
    @Email(message = "email.email")
    private String userEmail;

}
