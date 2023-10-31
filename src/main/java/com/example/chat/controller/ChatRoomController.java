package com.example.chat.controller;

import com.example.chat.payload.request.ChatRoomRequest;
import com.example.chat.servise.PublicChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Chat room", description = "Rest controller chat room")
@RestController
@AllArgsConstructor
@RequestMapping("api")
public class ChatRoomController {

    private final PublicChatRoomService publicChatRoomService;


    @Operation(summary = "New chat room (-TODO-)")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/save-new-chat-room")
    public void saveNewChatRoom(@RequestBody ChatRoomRequest chatRoomRequest) {
        publicChatRoomService.saveChatRoom(chatRoomRequest);

    }


}
