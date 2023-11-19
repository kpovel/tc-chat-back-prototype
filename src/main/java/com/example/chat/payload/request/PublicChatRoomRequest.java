package com.example.chat.payload.request;

import com.example.chat.dto.DtoChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublicChatRoomRequest extends ChatRoomRequest {

    private DtoChatRoom chatRoom;

}
