package com.example.chat.servise.impls;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.payload.request.MessageRequest;
import com.example.chat.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;


    @Transactional
    public Message saveMessage(MessageRequest messageRequest, User user, ChatRoom chatRoom) {
        Message message = new Message();
        message.setUser(user);
        message.setContent(messageRequest.getContent());
        message.setChatRoom(chatRoom);
        messageRepository.save(message);
        return message;
    }

    @Transactional
    public Page<Message> getPageMessage(long chatRoomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageRepository.findPageMessage(pageable, chatRoomId);
    }

    @Transactional
    public void deleteAllMessageByChatId(long chatId) {
        messageRepository.deleteAllByChatRoomId(chatId);
    }

    @Transactional
    public Optional<Message> getLastMessageByChatRoom(long chatRoomId) {
        return messageRepository.getLastMessageFromChatRoom(chatRoomId);
    }
}
