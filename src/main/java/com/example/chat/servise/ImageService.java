package com.example.chat.servise;

import com.example.chat.exception.CustomFileNotFoundException;
import com.example.chat.model.Image;
import com.example.chat.model.User;
import com.example.chat.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final FileService fileService;

    public void saveImageName(String imageName) throws CustomFileNotFoundException {
        User user = userService.getUserFromSecurityContextHolder();
        Image image = user.getImage();
        if (image.getName() != null) fileService.deleteFileFromStorage(image.getName());
        image.setName(imageName);
        imageRepository.save(image);
    }

//    public Page<Image> getImages(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return imagesRepository.findAll(pageable, true);
//    }

}
