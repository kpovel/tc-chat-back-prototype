package com.example.chat.servise;

import com.example.chat.repository.HashtagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;


}
