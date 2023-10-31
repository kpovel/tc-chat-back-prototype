package com.example.chat.servise;

import com.example.chat.model.PublicChatRoom;
import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;

    private final PublicChatRoomService publicChatRoomService;

    @Transactional
    public void saveMessage(Message message, Long chatRoomId) {
        User user = userService.getUserById(1L);
        PublicChatRoom publicChatRoom = publicChatRoomService.getChatRoom(chatRoomId);
        message.setUser(user);
        message.setDateOfCreated(LocalDateTime.now());
//        chatRoom.getMessage().add(message);
        message.setPublicChatRoom(publicChatRoom);
        messageRepository.save(message);
    }
}
