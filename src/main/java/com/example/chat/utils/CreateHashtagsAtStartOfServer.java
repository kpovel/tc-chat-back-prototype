package com.example.chat.utils;

import com.example.chat.model.Hashtag;
import com.example.chat.servise.HashtagService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateHashtagsAtStartOfServer implements ApplicationRunner {

    private HashtagService hashtagService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(hashtagService.countHashtags() == 0) {
            hashtagService.saveHashtag(new Hashtag("movies", "фільми", "one"));
            hashtagService.saveHashtag(new Hashtag("music", "музика", "two"));
        }

    }
}
