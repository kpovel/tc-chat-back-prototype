package com.example.chat.payload.request;

import com.example.chat.model.ChatRoomType;
import com.example.chat.model.Hashtag;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoomRequest {

    @NonNull
    private String chatRoomName;

    @NonNull
    private String chatRoomDescription;

    private Hashtag hashtag;

    @NonNull
    private Set<ChatRoomType> chatRoomType = new HashSet<>();


}
