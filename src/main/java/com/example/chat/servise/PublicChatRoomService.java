package com.example.chat.servise;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.Hashtag;
import com.example.chat.model.User;
import com.example.chat.payload.request.PublicChatRoomRequest;
import com.example.chat.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PublicChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final UserService userService;

    public void saveChatRoom(PublicChatRoomRequest publicChatRoomRequest) {
        User user = userService.getUserFromSecurityContextHolder();
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(publicChatRoomRequest.getChatRoom().getName());
        chatRoom.setUserAminChatRoom(user);
        Hashtag hashtag = new Hashtag();
        hashtag.setName(publicChatRoomRequest.getNewHashtag().get(0).getName()); // then recycle !!!!
        chatRoom.getHashtags().add(hashtag);
        chatRoom.getUsersChatRoom().add(user);
        chatRoomRepository.save(chatRoom);

    }

    @Transactional
    public ChatRoom getChatRoom(Long id) {
        return chatRoomRepository.findById(id).get();
    }

    public void saveTest(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public void removeChatRoom(Long id) {
        ChatRoom chatRoom = getChatRoom(id);
        chatRoomRepository.delete(chatRoom);
    }
}
