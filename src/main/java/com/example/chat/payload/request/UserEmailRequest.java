package com.example.chat.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEmailRequest {

    @NotBlank(message = "email.notblank")
    @Size(max = 50, message = "email.maxsize")
    @Email(message = "email.email")
    private String userEmail;

}
