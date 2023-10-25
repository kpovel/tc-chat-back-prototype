package com.example.chat.controller;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final ChatRoom chatRoom;

    private final Message message;

    @MessageMapping("/hello/{chatRoomId}")
    @SendTo("/topic/{chatRoomId}")
    public Message greeting(@DestinationVariable("chatRoomId") Long chatRoomId, Message message) throws Exception {
//        Long themeLongId = Long.parseLong(themeId);
        Thread.sleep(1000); // simulated delay
        return message;
    }

}