package com.example.chat.repository;

import com.example.chat.model.UserChatRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatRoomsRepository extends JpaRepository<UserChatRooms, Long> {
}
