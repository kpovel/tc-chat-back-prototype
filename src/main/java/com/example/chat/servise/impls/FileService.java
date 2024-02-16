package com.example.chat.servise.impls;

import com.example.chat.utils.exception.CustomFileNotFoundException;
import com.example.chat.utils.exception.FileStorageException;
import com.example.chat.config.FileStorageProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
public class FileService {

    private final Path fileStorageLocation;

    private static List<String> defaultAvatars = List.of("avatar-1.svg", "avatar-2.svg", "avatar-3.svg", "avatar-4.svg", "avatar-5.svg", "avatar-6.svg", "avatar-7.svg", "avatar-8.svg",
            "avatar-9.svg", "avatar-10.svg", "avatar-11.svg", "avatar-12.svg", "avatar-13.svg", "avatar-14.svg", "avatar-15.svg", "avatar-16.svg", "no-avatar.svg");

    @Autowired
    public FileService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    public String saveFileInStorage(MultipartFile file, String contentType) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            contentType = contentType.replaceAll("[+]*xml", "");
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + contentType.replaceAll("image/", ".");
            Path targetLocation = this.fileStorageLocation.resolve(resultFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return resultFilename;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
//            System.out.println(ex.getMessage());
        }
    }

    public Resource loadFileAsStorage(String fileName) throws CustomFileNotFoundException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new CustomFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new CustomFileNotFoundException("File not found " + fileName, ex.getMessage());
//            System.out.println(ex.getMessage());
        }
    }


    public void deleteFileFromStorage(String fileName) throws CustomFileNotFoundException {
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        try {
            Files.delete(targetLocation);
        } catch (IOException e) {
            throw new CustomFileNotFoundException("Image not found");
        }
    }

    public boolean defaultImage(String imageName) {
        for (String arr : defaultAvatars) {
            if (arr.equals(imageName)) return true;
        }
        return false;
    }


}