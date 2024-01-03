package com.example.chat.servise;

import com.example.chat.utils.exception.CustomFileNotFoundException;
import com.example.chat.utils.exception.FileStorageException;
import com.example.chat.config.FileStorageProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
//@NoArgsConstructor
public class FileService {

    private final Path fileStorageLocation;

    private static List<String> defaultAvatars = List.of("no-avatar.jpeg", "avatar-1.jpeg", "avatar-2.jpeg", "avatar-3.jpeg", "avatar-4.jpeg", "avatar-5.jpeg", "avatar-6.jpeg", "avatar-7.jpeg");

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
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + contentType.replaceAll("image/",".");
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

    public boolean notDefaultImage(String imageName) {
        for (String arr: defaultAvatars) {
            if(arr.equals(imageName)) return true;
        }
        return false;
    }

















//    public String saveWebImage(MultipartFile file, String contentType) {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        try {
//            if (fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//            contentType = contentType.replaceAll("image/", ".");
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFilename = uuidFile + contentType;
//            Path targetLocation = this.fileStorageLocation.resolve(resultFilename);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            Image image = new Image();
//            image.setImageName(resultFilename);
//            image.setWebImage(true);
//            imagesRepository.save(image);
//            return resultFilename;
//        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
////            System.out.println(ex.getMessage());
//        }
//    }
//    public CustomResponseMessage saveWordImage(MultipartFile file, Long wordId, String contentType) {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        try {
//            if (fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//            contentType = contentType.replaceAll("image/", ".");
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFilename = uuidFile + contentType;
//            Path targetLocation = this.storageLocationWordImage.resolve(resultFilename);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            Long imageId = wordService.getWord(wordId).getImages().getId();
//            Image image = imagesRepository.findById(imageId).get();
//            image.setImageName(resultFilename);
//            imagesRepository.save(image);
//            return new CustomResponseMessage(Message.SUCCESS_SAVE_TEXT_OF_PAGE);
//        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
////            System.out.println(ex.getMessage());
//        }
//    }
//
//    public CustomResponseMessage saveCategoryImage(MultipartFile file, Long categoryId, String contentType) {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        try {
//            if (fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//            contentType = contentType.replaceAll("image/", ".");
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFilename = uuidFile + contentType;
//            Path targetLocation = this.storageLocationCategoryImage.resolve(resultFilename);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            Long imageId = categoryService.getCategoryToEditor(categoryId).getImage().getId();
//            Image image = imagesRepository.findById(imageId).get();
//            image.setImageName(resultFilename);
//            imagesRepository.save(image);
//            return new CustomResponseMessage(Message.SUCCESS_SAVE_TEXT_OF_PAGE);
//        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
//        }
//    }
//
//    public Resource loadWebImages(String fileName) {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists()) {
//                return resource;
//            } else {
//                throw new MyFileNotFoundException("File not found " + fileName);
//
//            }
//        } catch (MalformedURLException ex) {
//            throw new MyFileNotFoundException("File not found " + fileName, ex);
////            System.out.println(ex.getMessage());
//        }
//    }
//
//    public Resource loadWordImages(String fileName) {
//        try {
//            Path filePath = this.storageLocationWordImage.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists()) {
//                return resource;
//            } else {
//                throw new MyFileNotFoundException("File not found " + fileName);
//            }
//        } catch (MalformedURLException ex) {
//            throw new MyFileNotFoundException("File not found " + fileName, ex);
//        }
//    }
//
//    public Resource loadCategoryImages(String fileName) {
//        try {
//            Path filePath = this.storageLocationCategoryImage.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists()) {
//                return resource;
//            } else {
//                throw new MyFileNotFoundException("File not found " + fileName);
//            }
//        } catch (MalformedURLException ex) {
//            throw new MyFileNotFoundException("File not found " + fileName, ex);
//        }
//    }
}
