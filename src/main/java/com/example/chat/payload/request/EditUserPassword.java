package com.example.chat.payload.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserPassword {

    @Size(min = 6,  message = "password.minsize")
    @Size(max = 72, message = "password.maxsize")
    private String oldPassword;

    @Size(min = 6, message = "password.minsize")
    @Size(max = 72, message = "password.maxsize")
    @Pattern(regexp = "^\\S*$", message = "password.spaces")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@<>'$^+#~!%*=?/;:_&|()+-])[A-Za-z\\d@<>'$^+#~!%*=?/;:_&|()+-]+$", message = "password.pattern")
    private String newPassword;

    public EditUserPassword() {
    }
}
