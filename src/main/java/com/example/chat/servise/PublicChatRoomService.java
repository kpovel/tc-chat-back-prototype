package com.example.chat.servise;

import com.example.chat.model.PublicChatRoom;
import com.example.chat.model.Hashtag;
import com.example.chat.model.User;
import com.example.chat.payload.request.ChatRoomRequest;
import com.example.chat.repository.PublicChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PublicChatRoomService {

    private final PublicChatRoomRepository publicChatRoomRepository;

    private final UserService userService;

    public void saveChatRoom(ChatRoomRequest chatRoomRequest) {
        User user = userService.getUserFromSecurityContextHolder();
        PublicChatRoom publicChatRoom = new PublicChatRoom();
        publicChatRoom.setName(chatRoomRequest.getChatRoom().getName());
        publicChatRoom.setUserAminChatRoom(user);
        Hashtag hashtag = new Hashtag();
        hashtag.setName(chatRoomRequest.getNewHashtag().get(0).getName()); // then recycle !!!!
        publicChatRoom.getHashtags().add(hashtag);
        publicChatRoom.getUsersChatRoom().add(user);
        publicChatRoomRepository.save(publicChatRoom);

    }

    @Transactional
    public PublicChatRoom getChatRoom(Long id) {
        return publicChatRoomRepository.findById(id).get();
    }

    public void saveTest(PublicChatRoom publicChatRoom) {
        publicChatRoomRepository.save(publicChatRoom);
    }

    @Transactional
    public void removeChatRoom(Long id) {
        PublicChatRoom publicChatRoom = getChatRoom(id);
        publicChatRoomRepository.delete(publicChatRoom);
    }
}
