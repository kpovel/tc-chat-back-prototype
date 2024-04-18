package com.example.chat.servise.impls;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.User;
import com.example.chat.model.UserChatRoom;
import com.example.chat.repository.UserChatRoomsRepository;
import com.example.chat.utils.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserChatRoomsService {


    private UserChatRoomsRepository userChatRoomsRepository;

    public UserChatRoom getUserChatRoomByChatRoomId(long charRoomId) {
        return userChatRoomsRepository.getUserChatRoomByChatRoomId(charRoomId)
                .orElseThrow((() -> new ObjectNotFoundException("Hashtag with id: " + charRoomId + " not found")));
    }

    @Transactional
    public void userExitFromChatRoom(User user, ChatRoom chatRoom) {
        UserChatRoom userChatRoom = getUserChatRoomByChatRoomId(chatRoom.getId());
        List<UserChatRoom> userChatRooms = user.getUserChatRooms();
        userChatRooms.removeIf(obj -> obj.getId().equals(userChatRoom.getId()));
        userChatRoomsRepository.delete(userChatRoom);
    }


}
