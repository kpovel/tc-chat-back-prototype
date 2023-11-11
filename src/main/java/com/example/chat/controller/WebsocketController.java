package com.example.chat.controller;

import com.example.chat.model.Message;
import com.example.chat.servise.ChatRoomService;
import com.example.chat.servise.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebsocketController {

    private final ChatRoomService chatRoomService;

    private final MessageService messageService;

    @MessageMapping("/hello/{chatRoomId}")
    @SendTo("/topic/{chatRoomId}")
    public Message greeting(@DestinationVariable("chatRoomId") Long chatRoomId, Message message) throws Exception {
        messageService.saveMessage(message, chatRoomId);
        chatRoomService.removeChatRoom(6L);
        Thread.sleep(1000); // simulated delay
        return message;
    }

}