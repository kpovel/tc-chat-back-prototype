package com.example.chat.servise.impls;

import com.example.chat.model.Hashtag;
import com.example.chat.repository.HashtagRepository;
import com.example.chat.utils.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;


    public Hashtag getHashtagById(long hashtagId) {
        return hashtagRepository.findById(hashtagId)
                .orElseThrow(() -> new ObjectNotFoundException("Hashtag with id: " + hashtagId + " not found"));
    }

    public Hashtag hetHashtagByName(String name) {
        return hashtagRepository.findHashtagByName(name)
                .orElseThrow(() -> new ObjectNotFoundException("Hashtag with name: '" + name + "' not found"));
    }
    public long countHashtags() {
        return hashtagRepository.count();
    }

    public void saveHashtag(Hashtag hashtag) {
        hashtagRepository.save(hashtag);
    }

}
