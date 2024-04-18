package com.example.chat.repository;

import com.example.chat.model.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserChatRoomsRepository extends JpaRepository<UserChatRoom, Long> {

    Optional<UserChatRoom> getUserChatRoomByChatRoomId(long chatRoomId);

}
