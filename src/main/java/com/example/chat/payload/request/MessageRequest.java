package com.example.chat.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequest {

    private String content;

    @NonNull
    private String currentChatUserUIID;

    boolean edited = false;
}
