package com.example.chat.payload.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserPasswordRequest {

    @Size(min = 6, message = "password.minsize")
    @Size(max = 72, message = "password.maxsize")
    @Pattern(regexp = "^\\S*$", message = "password.spaces")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@<>'$^+#~!%*=?/;:_&|()+-])[A-Za-z\\d@<>'$^+#~!%*=?/;:_&|()+-]+$", message = "password.pattern")
    private String userPassword;

}
