package com.example.chat.servise.impls;

import com.example.chat.model.*;
import com.example.chat.payload.request.CreatePublicChatRoomRequest;
import com.example.chat.payload.request.DemoDataPublicChat;
import com.example.chat.payload.request.EditChatRoomRequest;
import com.example.chat.repository.ChatRoomRepository;
import com.example.chat.utils.exception.ForbiddenException;
import com.example.chat.utils.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;


    @Transactional
    public void saveNewPublicChatRoomDemoData(User user, DemoDataPublicChat chatRoomRequest, Image image) {

        ChatRoom chatRoom = new ChatRoom();

        if(image.getName() == null) image.setName("no-image.svg");
        chatRoom.setImage(image);


        chatRoom.getChatRoomType().add(ChatRoomType.PUBLIC);
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
    public ChatRoom saveNewPublicChatRoom(User user, CreatePublicChatRoomRequest chatRoomRequest, Image image) {

        ChatRoom chatRoom = new ChatRoom();
        if(image.getName() == null)image.setName("no-image.svg");
        chatRoom.setImage(image);
        chatRoom.getChatRoomType().add(ChatRoomType.PUBLIC);
        chatRoom.setName(chatRoomRequest.getChatRoomName());

        UserChatRoom userChatRoom = new UserChatRoom();
        userChatRoom.setUser(user);
        userChatRoom.setChatName(chatRoomRequest.getChatRoomName());
        userChatRoom.setChatRoom(chatRoom);

        user.getUserChatRoomsAdmin().add(chatRoom);

        chatRoom.setUserAminChatRoom(user);
        chatRoom.getUserChatRooms().add(userChatRoom);

        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    public ChatRoom editDescriptionPublicChatRoom(User user, EditChatRoomRequest chatRoomRequest) {
        ChatRoom chatRoom = getChatRoomByUUID(chatRoomRequest.getUuid());
        if(chatRoom.getUserAminChatRoom().getId().equals(user.getId())) {
           chatRoom.setDescription(chatRoomRequest.getChatRoomDescription());
           chatRoomRepository.save(chatRoom);
           return chatRoom;
        }
        else throw new ForbiddenException("forbidden");
    }

    public ChatRoom editHashtagPublicChatRoom(User user, EditChatRoomRequest chatRoomRequest, Hashtag hashtag) {
        ChatRoom chatRoom = getChatRoomByUUID(chatRoomRequest.getUuid());
        if(chatRoom.getUserAminChatRoom().getId().equals(user.getId())) {
           chatRoom.setHashtag(hashtag);
           chatRoomRepository.save(chatRoom);
           return chatRoom;
        }
        else throw new ForbiddenException("forbidden");
    }
    public ChatRoom editImagePublicChatRoom(User user, ChatRoom chatRoom, String newImageName) {
        if(chatRoom.getUserAminChatRoom().getId().equals(user.getId())) {
           chatRoom.getImage().setName(newImageName);
           chatRoomRepository.save(chatRoom);
           return chatRoom;
        }
        else throw new ForbiddenException("forbidden");
    }

    @Transactional
    public void savePrivateChatRoom(EditChatRoomRequest editChatRoomRequest) {
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
    public ChatRoom getChatRoomByUUID(String chatRoomUUID) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findChatRoomByUuid(chatRoomUUID);
        if(chatRoomOptional.isPresent()){
            return chatRoomOptional.get();
        } else throw new ObjectNotFoundException("chatroom.not.found");
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
