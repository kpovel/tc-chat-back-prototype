package com.example.chat.controller;

import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.payload.request.MessageRequest;
import com.example.chat.servise.UserService;
import com.example.chat.servise.impls.MessageService;
import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Controller
@AllArgsConstructor
public class WebsocketController {

    private final MessageService messageService;

    private final UserService userService;

    @MessageMapping("/hello/{chatRoomUIID}")
    @SendTo("/topic/{chatRoomUIID}")
    @JsonView(JsonViews.ViewMessage.class)
    public Message greeting(@DestinationVariable("chatRoomUIID") String chatRoomUIID,  MessageRequest messageRequest) throws Exception {
        User user = userService.getUserByUIID(messageRequest.getCurrentChatUserUIID());
        Message message = messageService.saveMessage(messageRequest, user, chatRoomUIID);
        System.out.println(message.toString() + " Message save!!!  ***************************************");
        return message;
    }


}