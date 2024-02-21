package com.example.chat.payload.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CreatePublicChatRoomRequest {

    @NonNull
    private String chatRoomName;

    public CreatePublicChatRoomRequest() {
    }
}
