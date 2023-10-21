package com.example.chat.servise;

import com.example.chat.model.Image;
import com.example.chat.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

//    private void saveImageName(Long userId, String imageName) {
//        Optional<Image> imageOptional = imageRepository.findById(userId);
//        if (imageOptional.isPresent()) {
//            Image avatar = imageOptional.get();
//            if (avatar.getName() != null) deleteImageFromDirectory(avatar.getImageName());
//            avatar.setName(imageName);
//            imagesRepository.save(avatar);
////            return userRepository.save(user);
//        } else {
//            throw new IllegalArgumentException("User with id " + userId + " not found");
//        }
//    }

//    public Page<Image> getImages(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return imagesRepository.findAll(pageable, true);
//    }

}
