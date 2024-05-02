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
    @Size(min=3, max = 50, message = "field.size.name.chat")
    private String chatRoomName;

    public CreatePublicChatRoomRequest() {
    }
}
