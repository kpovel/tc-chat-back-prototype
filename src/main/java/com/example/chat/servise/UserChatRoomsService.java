package com.example.chat.servise;

import com.example.chat.repository.UserChatRoomsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserChatRoomsService {


    private UserChatRoomsRepository userChatRoomsRepository;


}
