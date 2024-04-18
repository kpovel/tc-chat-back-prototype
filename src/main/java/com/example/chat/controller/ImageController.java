package com.example.chat.controller;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.User;
import com.example.chat.servise.impls.ChatRoomService;
import com.example.chat.servise.impls.FileService;
import com.example.chat.servise.impls.ImageService;
import com.example.chat.servise.impls.UserServiceImpl;
import com.example.chat.utils.JsonViews;
import com.example.chat.utils.exception.FileFormatException;
import com.example.chat.utils.exception.InvalidDataException;
import com.example.chat.utils.validate.ValidateFields;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("api")
@Data
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController {

    private final FileService fileService;

    private final ImageService imageService;

    private final UserServiceImpl userService;

    private final MessageSource messageSource;

    private final ChatRoomService chatRoomService;

    @Operation(summary = "User onboarding - save user avatar")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/user/avatar/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String contentType = file.getContentType();
            if (ValidateFields.isSupportedImageType(contentType)) {
                User user = userService.getUserFromSecurityContextHolder();
                if (user.isOnboardingEnd()) throw new InvalidDataException("User onboarding is END!");
                String imageName = fileService.saveFileInStorage(file, contentType.replaceAll("image/", "."));
                imageService.saveImageName(user, imageName);
                return ResponseEntity.ok(imageName);
            } else throw new FileFormatException("error.file.format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Помилка обробки файлу: " + e.getMessage());
        }
    }

    @PutMapping("/edit-image/public-chat-room/{uuid}")
    @Operation(summary = "Edit public chat room step five", description = "Step five - upload image chat room")
    @SecurityRequirement(name = "Bearer Authentication")
    @JsonView(JsonViews.ViewFieldUUIDChatList.class)
    public ResponseEntity<?> editImagePublicChatRoom(@PathVariable String uuid,
                                                     @RequestParam("image") MultipartFile file) {
        try {
            String contentType = file.getContentType();
            if (ValidateFields.isSupportedImageType(contentType)) {
                User user = userService.getUserFromSecurityContextHolder();
                String imageName = fileService.saveFileInStorage(file, contentType.replaceAll("image/", "."));
                ChatRoom chatRoom = chatRoomService.getChatRoomByUUID(uuid);
                if (!chatRoom.getImage().getName().equals("no-image.svg"))
                    fileService.deleteFileFromStorage(chatRoom.getImage().getName());
                return ResponseEntity.ok(chatRoomService.editImagePublicChatRoom(user, chatRoom, imageName));

            }
            else throw new FileFormatException("error.file.format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Помилка обробки файлу: " + e.getMessage());
        }
    }

    @Operation(summary = "User avatar by image name")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/user-image/{fileName:.+}")
    public ResponseEntity<byte[]> categoryImage(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Resource resource = fileService.loadFileAsStorage(fileName);
        InputStream in = resource.getInputStream();
        byte[] imageBytes = IOUtils.toByteArray(in);
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG); // встановити тип контенту як image/jpeg, або image/png, залежно від формату зображення
        headers.setContentLength(imageBytes.length);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(resource.getFilename()).build());
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @Operation(summary = "User onboarding - collection default avatars")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/default-avatars")
    public ResponseEntity<List<String>> defaultAvatars() {
        List<String> defaultAvatars = List.of("avatar-1.svg", "avatar-2.svg", "avatar-3.svg", "avatar-4.svg", "avatar-5.svg", "avatar-6.svg", "avatar-7.svg", "avatar-8.svg",
                "avatar-9.svg", "avatar-10.svg", "avatar-11.svg", "avatar-12.svg", "avatar-13.svg", "avatar-14.svg", "avatar-15.svg", "avatar-16.svg");
        return ResponseEntity.ok(defaultAvatars);
    }

}
