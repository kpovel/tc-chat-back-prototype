package com.example.chat.servise.impls;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.ChatRoomType;
import com.example.chat.model.User;
import com.example.chat.model.UserChatRooms;
import com.example.chat.payload.request.ChatRoomRequest;
import com.example.chat.payload.request.PublicChatRoomRequest;
import com.example.chat.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final UserServiceImpl userService;

    public void savePublicChatRoom(PublicChatRoomRequest chatRoomRequest) {
        User user = userService.getUserFromSecurityContextHolder();
        ChatRoom chatRoom = new ChatRoom();
        Set<ChatRoomType> chatRoomType = chatRoomRequest.getChatRoomType();
        chatRoom.setName(chatRoomRequest.getChatRoom().getName());
        chatRoom.setUserAminChatRoom(user);
//        chatRoom.getHashtags().add(hashtag);
        chatRoom.getUsersChatRoom().add(user);
        chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public void savePrivateChatRoom(ChatRoomRequest chatRoomRequest) {
        User userInitiatorConversation = userService.getUserFromSecurityContextHolder();
        User otherUser = userService.getUserById(chatRoomRequest.getToUserId());
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.getChatRoomType().add(ChatRoomType.PRIVATE);
        UserChatRooms userInitiatorChat = new UserChatRooms();
        userInitiatorChat.setChatName(otherUser.getUserLogin());
        userInitiatorChat.setUser(userInitiatorConversation);
        userInitiatorChat.setChatRoom(chatRoom);
        UserChatRooms otherUserChat = new UserChatRooms();
        otherUserChat.setChatName(userInitiatorConversation.getUserLogin());
        otherUserChat.setUser(otherUser);
        otherUserChat.setChatRoom(chatRoom);
        chatRoom.getUserChatRooms().add(userInitiatorChat);
        chatRoom.getUserChatRooms().add(otherUserChat);
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
