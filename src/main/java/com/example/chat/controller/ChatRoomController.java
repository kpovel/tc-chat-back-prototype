package com.example.chat.controller;

import com.example.chat.model.Hashtag;
import com.example.chat.model.Image;
import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.payload.request.ChatRoomRequest;
import com.example.chat.servise.UserService;
import com.example.chat.servise.impls.ChatRoomService;
import com.example.chat.servise.impls.FileService;
import com.example.chat.servise.impls.HashtagService;
import com.example.chat.utils.exception.FileFormatException;
import com.example.chat.utils.validate.ValidateFields;
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


    @PostMapping("/public-chat-room/create-demo-data")
    @Operation(summary = "(DEMO!!!) Create New public chat room", description = "Create demo data for test")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> saveNewPublicChatRoom(@RequestPart(name = "file", required = false) MultipartFile file,
                                                   @RequestPart(name = "chatRoom") ChatRoomRequest chatRoomRequest) {
        Image image = new Image();
        String contentType = file.getContentType();
        if (ValidateFields.isSupportedImageType(contentType)) {
            String imageName = fileService.saveFileInStorage(file, contentType.replaceAll("image/", "."));
            image.setName(imageName);
        } else throw new FileFormatException("Дозволено тільки зображення");

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

    @PostMapping("/create-private-chat-room/to-user/{userId}")
    @Operation(summary = "New chat room (-TODO-)")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> saveNewPrivateChatRoom(@PathVariable long userId, @RequestBody ChatRoomRequest chatRoomRequest) {
        //TODO: userId replace with uuid
//        chatRoomService.savePrivateChatRoom(chatRoomRequest);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/get-chat-room/{chatRoomUUID}")
    @Operation(summary = "Get chat room (--TODO--)")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getChatRoom(@PathVariable String chatRoomUUID) {
        //TODO
        return null;
    }

    @GetMapping("/chat-messages/{id}")
    @Operation
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<Message>> allChatMessages(@PathVariable("id") long chatId) {


        return ResponseEntity.ok(null);
    }


}
