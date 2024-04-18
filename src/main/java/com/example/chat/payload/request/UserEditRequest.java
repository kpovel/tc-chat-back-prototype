package com.example.chat.payload.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditRequest {

    @Size(min = 3, max = 50, message = "user.name.size")
    private String name;

    @Size(max = 300, message = "user.about.size")
    private String about;

    private String defaultAvatar;


}
