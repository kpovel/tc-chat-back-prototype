package com.example.chat.servise.impls;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.payload.request.MessageRequest;
import com.example.chat.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final ChatRoomService chatRoomService;

    @Transactional
    public Message saveMessage(MessageRequest messageRequest, User user, String chatRoomUUID) {
        ChatRoom chatRoom = chatRoomService.getChatRoomByUUID(chatRoomUUID);
        Message message = new Message();
        message.setUser(user);

        message.setContent(messageRequest.getContent());

        chatRoom.getMessages().add(message);
        message.setChatRoom(chatRoom);
        messageRepository.save(message);
        return message;
    }
}
