package com.example.chat.controller;

import com.example.chat.exception.CustomFileNotFoundException;
import com.example.chat.exception.FileFormatException;
import com.example.chat.servise.FileService;
import com.example.chat.servise.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("api")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class ImageController {

    private final FileService fileService;
    private final ImageService imageService;

    @Operation(summary = "User onboarding - save user avatar")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/user/avatar/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) throws CustomFileNotFoundException {
        String contentType = file.getContentType();
        assert contentType != null;
        if (contentType.equalsIgnoreCase("image/jpeg") || contentType.equals("image/png")
                || contentType.equals("image/webp") || contentType.equals("image/jpg")) {
            String imageName = fileService.saveFileInStorage(file,  contentType.replaceAll("image/","."));
            imageService.saveImageName(imageName);
            return ResponseEntity.ok(imageName);
        } else throw new FileFormatException("Дозволено тільки зображення");
    }

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
}
