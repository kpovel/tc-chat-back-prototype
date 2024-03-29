package com.example.chat.payload.request;

import com.example.chat.model.Hashtag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditChatRoomRequest {

    @NotNull
    private String uuid;

    @Size(max = 300, message = "field.max.description")
    private String chatRoomDescription;

    private Hashtag hashtag;

    public EditChatRoomRequest() {
    }
}
