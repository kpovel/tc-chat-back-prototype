package com.example.chat.payload.request;

import com.example.chat.dto.DtoChatRoom;
import com.example.chat.dto.DtoHashtag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PublicChatRoomRequest {

    private DtoChatRoom chatRoom;
//    private Set<ChatRoomType> chatRoomType = new HashSet<>();

    private List<DtoHashtag> newHashtag;

    private List<Long> hashtagsId;

    public PublicChatRoomRequest() {
    }
}
