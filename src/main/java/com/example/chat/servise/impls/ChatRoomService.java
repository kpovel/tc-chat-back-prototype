package com.example.chat.servise.impls;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.User;
import com.example.chat.model.UserChatRoom;
import com.example.chat.payload.request.ChatRoomRequest;
import com.example.chat.repository.ChatRoomRepository;
import com.example.chat.utils.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final UserServiceImpl userService;

    public void saveNewPublicChatRoom(ChatRoomRequest chatRoomRequest) {
        User user = userService.getUserFromSecurityContextHolder();

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setChatRoomType(chatRoomRequest.getChatRoomType());
        chatRoom.setName(chatRoomRequest.getChatRoomName());
        chatRoom.setDescription(chatRoomRequest.getChatRoomDescription());
        chatRoom.setHashtag(chatRoomRequest.getHashtag());

        UserChatRoom userChatRoom = new UserChatRoom();
        userChatRoom.setUser(user);
        userChatRoom.setChatName(chatRoomRequest.getChatRoomName());
        userChatRoom.setChatRoom(chatRoom);

        user.getUserChatRoomsAdmin().add(chatRoom);
        chatRoom.setUserAminChatRoom(user);
        chatRoom.getUserChatRooms().add(userChatRoom);

        chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public void savePrivateChatRoom(ChatRoomRequest chatRoomRequest) {
//        User userInitiatorConversation = userService.getUserFromSecurityContextHolder();
//        User otherUser = userService.getUserById(chatRoomRequest.getToUserId());
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.getChatRoomType().add(ChatRoomType.PRIVATE);
//        UserChatRoom userInitiatorChat = new UserChatRoom();
//        userInitiatorChat.setChatName(otherUser.getUserLogin());
//        userInitiatorChat.setUser(userInitiatorConversation);
//        userInitiatorChat.setChatRoom(chatRoom);
//        UserChatRoom otherUserChat = new UserChatRoom();
//        otherUserChat.setChatName(userInitiatorConversation.getUserLogin());
//        otherUserChat.setUser(otherUser);
//        otherUserChat.setChatRoom(chatRoom);
//        chatRoom.getUserChatRooms().add(userInitiatorChat);
//        chatRoom.getUserChatRooms().add(otherUserChat);
//        chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public ChatRoom getChatRoom(String chatRoomUUID) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findChatRoomByUuid(chatRoomUUID);
        if(chatRoomOptional.isPresent()){
            return chatRoomOptional.get();
            //TODO: Localisation response
        } else throw new ObjectNotFoundException("Chat room with id: " + chatRoomUUID + " not found");
    }

    public void saveTest(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public void removeChatRoom(long chatRoomId) {
//        ChatRoom chatRoom = getChatRoom(chatRoomId);
//        chatRoomRepository.delete(chatRoom);
    }
}
