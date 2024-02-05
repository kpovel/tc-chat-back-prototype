package com.example.chat.payload.request;

import com.example.chat.model.ChatRoomType;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CreatePublicChatRoomRequest {

    @NonNull
    private String chatRoomName;

    @NonNull
//    @Size(min = 1)
    private Set<ChatRoomType> chatRoomType = new HashSet<>();

    public CreatePublicChatRoomRequest() {
    }
}
