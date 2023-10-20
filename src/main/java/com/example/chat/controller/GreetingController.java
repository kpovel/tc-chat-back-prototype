package com.example.chat.controller;

import com.example.chat.repository.ChatThemeRepository;
import com.example.chat.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final ChatThemeRepository themeRepository;

    @MessageMapping("/hello/{themeId}")
    @SendTo("/topic/{themeId}")
    public Message greeting(@DestinationVariable("themeId") Long themeId, Message message) throws Exception {
//        Long themeLongId = Long.parseLong(themeId);
        Thread.sleep(1000); // simulated delay
        return message;
    }

}