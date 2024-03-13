package com.example.chat.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CreatePublicChatRoomRequest {

    @NotNull(message = "field.not.empty")
    @Size(max = 60, message = "field.max.size")
    @Size(min = 1, message = "field.min.size")
    private String chatRoomName;

    public CreatePublicChatRoomRequest() {
    }
}
