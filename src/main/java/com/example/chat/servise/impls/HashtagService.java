package com.example.chat.servise.impls;

import com.example.chat.model.Hashtag;
import com.example.chat.repository.HashtagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;


    public Long countHashtags() {
        return hashtagRepository.count();
    }

    public void saveHashtag(Hashtag hashtag) {
        hashtagRepository.save(hashtag);
    }

}
