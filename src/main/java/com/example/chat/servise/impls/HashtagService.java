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
        Optional<Hashtag> hashtagOptional = hashtagRepository.findById(hashtagId);
        if(hashtagOptional.isPresent()) return hashtagOptional.get();
        else throw new ObjectNotFoundException("Hashtag with id: " + hashtagId + " not found");
    }
    public long countHashtags() {
        return hashtagRepository.count();
    }

    public void saveHashtag(Hashtag hashtag) {
        hashtagRepository.save(hashtag);
    }

}
