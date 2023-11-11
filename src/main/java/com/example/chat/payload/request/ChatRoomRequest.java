package com.example.chat.payload.request;

import com.example.chat.dto.DtoChatRoom;
import com.example.chat.dto.DtoHashtag;
import com.example.chat.model.ChatRoomType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomRequest {

    @NonNull
    private Set<ChatRoomType> chatRoomType = new HashSet<>();

    private Long toUserId;


}
