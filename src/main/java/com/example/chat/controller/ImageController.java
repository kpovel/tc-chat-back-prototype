package com.example.chat.controller;

import com.example.chat.model.User;
import com.example.chat.servise.impls.FileService;
import com.example.chat.servise.impls.ImageService;
import com.example.chat.servise.impls.UserServiceImpl;
import com.example.chat.utils.exception.FileFormatException;
import com.example.chat.utils.exception.InvalidDataException;
import com.example.chat.utils.validate.ValidateFields;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("api")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController {

    private final FileService fileService;
    private final ImageService imageService;
    private final UserServiceImpl userService;

    @Operation(summary = "User onboarding - save user avatar")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/user/avatar/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String contentType = file.getContentType();
            if (ValidateFields.isSupportedImageType(contentType)) {
                User user = userService.getUserFromSecurityContextHolder();
                if(user.isOnboardingEnd()) throw new InvalidDataException("User onboarding is END!");
                String imageName = fileService.saveFileInStorage(file, contentType.replaceAll("image/", "."));
                imageService.saveImageName(user, imageName);
                return ResponseEntity.ok(imageName);
            } else throw new FileFormatException("Дозволено тільки зображення");
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
        List<String> defaultAvatars = List.of("no-avatar.jpeg", "avatar-1.jpeg", "avatar-2.jpeg", "avatar-3.jpeg", "avatar-4.jpeg",
                "avatar-5.jpeg", "avatar-6.jpeg", "avatar-7.jpeg");
        return ResponseEntity.ok(defaultAvatars);
    }
}
