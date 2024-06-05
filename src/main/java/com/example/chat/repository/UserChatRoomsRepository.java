package com.example.chat.repository;

import com.example.chat.model.User;
import com.example.chat.model.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChatRoomsRepository extends JpaRepository<UserChatRoom, Long> {

    Optional<UserChatRoom> getUserChatRoomByChatRoomIdAndUserId(long chatRoomId, long userId);

    List<UserChatRoom> getAllByChatRoomId(long chatRoomId);

}
