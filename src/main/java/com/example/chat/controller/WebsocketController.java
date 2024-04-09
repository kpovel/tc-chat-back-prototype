package com.example.chat.controller;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.payload.request.MessageRequest;
import com.example.chat.servise.UserService;
import com.example.chat.servise.impls.ChatRoomService;
import com.example.chat.servise.impls.MessageService;
import com.example.chat.utils.JsonViews;
import com.example.chat.utils.exception.ObjectNotFoundException;
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

    private final ChatRoomService chatRoomService;

    @MessageMapping("/hello/{chatRoomUUID}")
    @SendTo("/topic/{chatRoomUUID}")
    @JsonView(JsonViews.ViewMessage.class)
    public Message greeting(@DestinationVariable("chatRoomUUID") String chatRoomUUID,  MessageRequest messageRequest) {
        try {
        User user = userService.getUserByUUID(messageRequest.getCurrentChatUserUUID());
        ChatRoom chatRoom = chatRoomService.getChatRoomByUUID(chatRoomUUID);
        return messageService.saveMessage(messageRequest, user, chatRoom);
        } catch (ObjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}