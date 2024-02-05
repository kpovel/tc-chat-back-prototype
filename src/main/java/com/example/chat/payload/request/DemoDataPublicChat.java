package com.example.chat.payload.request;

import com.example.chat.model.ChatRoomType;
import com.example.chat.model.Hashtag;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class DemoDataPublicChat {

    @NonNull
    private String chatRoomName;

    @NonNull
    private String chatRoomDescription;

    private Hashtag hashtag;

    @NonNull
    private Set<ChatRoomType> chatRoomType = new HashSet<>();

    public DemoDataPublicChat() {
    }
}
