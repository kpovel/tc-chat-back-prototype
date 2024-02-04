package com.example.chat.servise.impls;

import com.example.chat.servise.impls.FileService;
import com.example.chat.utils.exception.CustomFileNotFoundException;
import com.example.chat.model.Image;
import com.example.chat.model.User;
import com.example.chat.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private final FileService fileService;

    public void saveImageName(User user, String imageName) throws CustomFileNotFoundException {
        Image image = user.getImage();
        String oldNameImage = image.getName();
        if (image.getName() != null && !fileService.defaultImage(oldNameImage)) fileService.deleteFileFromStorage(oldNameImage);
        image.setName(imageName);
        imageRepository.save(image);
    }

//    public Page<Image> getImages(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return imagesRepository.findAll(pageable, true);
//    }

}
