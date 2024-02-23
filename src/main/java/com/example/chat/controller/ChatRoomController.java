package com.example.chat.controller;

import com.example.chat.model.*;
import com.example.chat.payload.request.CreatePublicChatRoomRequest;
import com.example.chat.payload.request.DemoDataPublicChat;
import com.example.chat.payload.request.EditChatRoomRequest;
import com.example.chat.servise.UserService;
import com.example.chat.servise.impls.ChatRoomService;
import com.example.chat.servise.impls.FileService;
import com.example.chat.servise.impls.HashtagService;
import com.example.chat.utils.JsonViews;
import com.example.chat.utils.exception.FileFormatException;
import com.example.chat.utils.exception.InvalidDataException;
import com.example.chat.utils.validate.ValidateFields;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(name = "Chat room", description = "Rest controller chat room")
@RestController
@AllArgsConstructor
@RequestMapping("api")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    private final HashtagService hashtagService;

    private final UserService userService;

    private final FileService fileService;


    @PostMapping("/public-chat-room/create/demo-data")
    @Operation(summary = "(DEMO!!!) Create New public chat room", description = "Create demo data for test")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> saveNewPublicChatRoomDemo(@RequestPart(name = "file", required = false) MultipartFile file,
                                                       @RequestPart(name = "chatRoom") DemoDataPublicChat chatRoomRequest) {
        Image image = new Image();
        if(file != null) {
            String contentType = file.getContentType();
            if (ValidateFields.isSupportedImageType(contentType)) {
                String imageName = fileService.saveFileInStorage(file, contentType.replaceAll("image/", "."));
                image.setName(imageName);
            } else throw new FileFormatException("Дозволено тільки зображення");
        }
        User user = userService.getUserFromSecurityContextHolder();
        if (chatRoomRequest.getHashtag() != null) {
            Hashtag hashtag = hashtagService.getHashtagById(chatRoomRequest.getHashtag().getId());
            chatRoomRequest.setHashtag(hashtag);
        } else {
            chatRoomRequest.setHashtag(null);
        }
        chatRoomService.saveNewPublicChatRoomDemoData(user, chatRoomRequest, image);

        return ResponseEntity.ok("Success");
    }

    @PostMapping("/public-chat-room/create")
    @Operation(summary = "Create New public chat room step one", description = "Step one - name, chat type and image file")
    @SecurityRequirement(name = "Bearer Authentication")
    @JsonView(JsonViews.ViewFieldChat.class)
    public ResponseEntity<ChatRoom> createNewPublicChatRoom(@RequestPart(name = "file", required = false) MultipartFile file,
                                                            @RequestPart(name = "chatRoom") CreatePublicChatRoomRequest chatRoomRequest) {
        Image image = new Image();
        if(file != null) {
            String contentType = file.getContentType();
            if (ValidateFields.isSupportedImageType(contentType)) {
                String imageName = fileService.saveFileInStorage(file, contentType.replaceAll("image/", "."));
                image.setName(imageName);
            } else throw new FileFormatException("Дозволено тільки зображення");
            //TODO: add GlobalHandler FileFormatException
        }
        User user = userService.getUserFromSecurityContextHolder();
        return ResponseEntity.ok(chatRoomService.saveNewPublicChatRoom(user, chatRoomRequest, image));
    }

    @PutMapping("/public-chat-room/edit-description")
    @Operation(summary = "Edit public chat room step two", description = "Step two - chat description")
    @SecurityRequirement(name = "Bearer Authentication")
    @JsonView(JsonViews.ViewFieldUiidChatList.class)
    public ResponseEntity<ChatRoom> editDescriptionPublicChatRoom(@RequestBody EditChatRoomRequest chatRoomRequest) {
        User user = userService.getUserFromSecurityContextHolder();
        if(chatRoomRequest.getChatRoomDescription().length() > 300 ) throw new InvalidDataException("chat description field max size 300 characters");
        return ResponseEntity.ok(chatRoomService.editDescriptionPublicChatRoom(user, chatRoomRequest));
    }

    @PutMapping("/public-chat-room/edit-hashtag")
    @Operation(summary = "Edit public chat room step four", description = "Step three - chat hashtag ")
    @SecurityRequirement(name = "Bearer Authentication")
    @JsonView(JsonViews.ViewFieldUiidChatList.class)
    public ResponseEntity<ChatRoom> editHashtagPublicChatRoom(@RequestBody EditChatRoomRequest chatRoomRequest) {
        User user = userService.getUserFromSecurityContextHolder();
        Hashtag hashtag = hashtagService.getHashtagById(chatRoomRequest.getHashtag().getId());
        return ResponseEntity.ok(chatRoomService.editHashtagPublicChatRoom(user, chatRoomRequest, hashtag));
    }

    @GetMapping("/get-chat-room/{chatRoomUUID}")
    @Operation(summary = "Get chat room by uiid")
    @SecurityRequirement(name = "Bearer Authentication")
    @JsonView(JsonViews.ViewFieldChatRoom.class)
    public ResponseEntity<?> getChatRoomByUIID(@PathVariable String chatRoomUUID) {
        User user = userService.getUserFromSecurityContextHolder();
        ChatRoom chatRoom = chatRoomService.getChatRoomByUIID(chatRoomUUID);
        chatRoom.setCurrentChatUserUIID(user.getUiid());
        return ResponseEntity.ok(chatRoom);
    }

    @PostMapping("/create-private-chat-room/to-user/{userId}")
    @Operation(summary = "New chat room (-TODO-)")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> saveNewPrivateChatRoom(@PathVariable long userId, @RequestBody EditChatRoomRequest editChatRoomRequest) {
        //TODO: userId replace with uuid
//        chatRoomService.savePrivateChatRoom(chatRoomRequest);

        return ResponseEntity.ok("ok");
    }



    @GetMapping("/chat-messages/{id}")
    @Operation
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<Message>> allChatMessages(@PathVariable("id") long chatId) {


        return ResponseEntity.ok(null);
    }


}
