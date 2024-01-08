package com.example.chat.controller;

import com.example.chat.model.ChatRoomType;
import com.example.chat.model.Message;
import com.example.chat.payload.request.PublicChatRoomRequest;
import com.example.chat.servise.impls.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;



@Tag(name = "Chat room", description = "Rest controller chat room")
@RestController
@AllArgsConstructor
@RequestMapping("api")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class ChatRoomController {

    private final ChatRoomService chatRoomService;


    @PostMapping("/save-new-chat-room")
    @Operation(summary = "New chat room (-TODO-)")
    @SecurityRequirement(name = "Bearer Authentication")
    public void saveNewChatRoom(@RequestBody PublicChatRoomRequest chatRoomRequest) {
        Set<ChatRoomType> chatRoomType = chatRoomRequest.getChatRoomType();
        if(chatRoomType.contains(ChatRoomType.PRIVATE)){
            chatRoomService.savePrivateChatRoom(chatRoomRequest);
            System.out.println("PRIVATE CHAT");
        } else {
            chatRoomService.savePublicChatRoom(chatRoomRequest);
        }


    }

    @GetMapping("/chat-messages/{id}")
    @Operation
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<Message>> allChatMessages(@PathVariable("id") long chatId) {


        return ResponseEntity.ok(null);
    }


}
