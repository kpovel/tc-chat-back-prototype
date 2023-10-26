package com.example.chat.servise;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.Hashtag;
import com.example.chat.model.User;
import com.example.chat.payload.request.ChatRoomRequest;
import com.example.chat.repository.ChatRoomRepository;
import io.jsonwebtoken.lang.Arrays;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final UserService userService;

    public void saveChatRoom(ChatRoomRequest chatRoomRequest) {
        User user = userService.getUserFromSecurityContextHolder();
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setChatRoomType(chatRoomRequest.getChatRoomType());
        chatRoom.setName(chatRoomRequest.getChatRoom().getName());
        chatRoom.setUserAminChatRoom(user);
        Hashtag hashtag = new Hashtag();
        hashtag.setName(chatRoomRequest.getNewHashtag().getName());
//        List<Hashtag> hashtagList = new ArrayList<>();
//        hashtagList.add(hashtag);
//        chatRoom.setHashtags(hashtagList);
        chatRoom.getHashtags().add(hashtag);




        chatRoomRepository.save(chatRoom);

    }
}
