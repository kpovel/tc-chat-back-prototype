package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.repository.ChatThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

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