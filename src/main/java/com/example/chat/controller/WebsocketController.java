package com.example.chat.controller;

import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.payload.request.MessageRequest;
import com.example.chat.servise.UserService;
import com.example.chat.servise.impls.MessageService;
import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebsocketController {

    private final MessageService messageService;

    private final UserService userService;

    @MessageMapping("/hello/{chatRoomUUID}")
    @SendTo("/topic/{chatRoomUUID}")
    @JsonView(JsonViews.ViewMessage.class)
    public Message greeting(@DestinationVariable("chatRoomUUID") String chatRoomUUID,  MessageRequest messageRequest) throws Exception {
        User user = userService.getUserByUUID(messageRequest.getCurrentChatUserUUID());
        Message message = messageService.saveMessage(messageRequest, user, chatRoomUUID);
        System.out.println(message.toString() + " Message save!!!  ***************************************");
        return message;
    }


}