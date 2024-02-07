package com.example.chat.payload.request;

import com.example.chat.model.Hashtag;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class DemoDataPublicChat {

    @NonNull
    private String chatRoomName;

    @NonNull
    private String chatRoomDescription;

    private Hashtag hashtag;

    public DemoDataPublicChat() {
    }
}
