package com.example.chat.controller;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.Message;
import com.example.chat.servise.impls.ChatRoomService;
import com.example.chat.servise.impls.MessageService;
import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final ChatRoomService chatRoomService;

    @GetMapping("/page/messages/chat-room/{chatroomUuid}")
    @JsonView(JsonViews.ViewMessage.class)
    public ResponseEntity<?> getPageMessage(@PathVariable String chatroomUuid,
                                            @RequestParam(value = "messageId") long messageId,
                                            @RequestParam(value = "size", defaultValue = "20", required = false) int size) {
        ChatRoom chatRoom = chatRoomService.getChatRoomByUUID(chatroomUuid);
        List<Message> messages = messageService.getMessagesByChatRoom(chatRoom.getId(), messageId, size);
        return ResponseEntity.ok(messages);
    }

}
