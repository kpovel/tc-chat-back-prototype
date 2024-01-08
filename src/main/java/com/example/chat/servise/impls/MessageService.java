package com.example.chat.servise.impls;

import com.example.chat.model.ChatRoom;
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

    private final UserServiceImpl userService;

    private final ChatRoomService chatRoomService;

    @Transactional
    public void saveMessage(Message message, long chatRoomId) {
        User user = userService.getUserById(1L);
        ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomId);
        message.setUser(user);
        message.setDateOfCreated(LocalDateTime.now());
//        chatRoom.getMessage().add(message);
        message.setChatRoom(chatRoom);
        messageRepository.save(message);
    }
}
