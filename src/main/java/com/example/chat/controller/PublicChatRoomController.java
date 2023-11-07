package com.example.chat.controller;

import com.example.chat.model.Message;
import com.example.chat.payload.request.PublicChatRoomRequest;
import com.example.chat.servise.PublicChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Chat room", description = "Rest controller chat room")
@RestController
@AllArgsConstructor
@RequestMapping("api")
public class PublicChatRoomController {

    private final PublicChatRoomService publicChatRoomService;


    @PostMapping("/save-new-chat-room")
    @Operation(summary = "New chat room (-TODO-)")
    @SecurityRequirement(name = "Bearer Authentication")
    public void saveNewChatRoom(@RequestBody PublicChatRoomRequest publicChatRoomRequest) {
        publicChatRoomService.saveChatRoom(publicChatRoomRequest);

    }

    @GetMapping("/chat-messages/{id}")
    @Operation
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<Message>> allChatMessages(@PathVariable("id") Long chatId) {


        return ResponseEntity.ok(null);
    }


}
