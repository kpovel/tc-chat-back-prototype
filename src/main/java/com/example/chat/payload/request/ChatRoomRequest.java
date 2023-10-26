package com.example.chat.payload.request;

import com.example.chat.dto.DtoChatRoom;
import com.example.chat.dto.DtoHashtag;
import com.example.chat.model.ChatRoomType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ChatRoomRequest {

    private DtoChatRoom chatRoom;
    private Set<ChatRoomType> chatRoomType = new HashSet<>();

    private DtoHashtag newHashtag;

    private List<Long> hashtagsId;

    private Long userId;

    public ChatRoomRequest() {
    }
}
