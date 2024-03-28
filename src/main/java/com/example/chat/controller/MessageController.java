package com.example.chat.controller;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.Message;
import com.example.chat.payload.response.PageMessagesResponse;
import com.example.chat.servise.impls.ChatRoomService;
import com.example.chat.servise.impls.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final ChatRoomService chatRoomService;

    @GetMapping("/page/messages/chat-room/{chatroomUuid}")
    public ResponseEntity<?> getPageMessage(@PathVariable String chatroomUuid,
                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "20", required = false) int size) {
        if (page <= 0) page = 1;
        ChatRoom chatRoom = chatRoomService.getChatRoomByUUID(chatroomUuid);
        Page<Message> pageMessage = messageService.getPageMessage(chatRoom.getId(), page, size);

        return ResponseEntity.ok(new PageMessagesResponse(
                                                        pageMessage.getContent(),
                                                        pageMessage.getTotalPages(),
                                                        pageMessage.getTotalElements(),
                                                        pageMessage.getNumber()
        ));
    }

}
