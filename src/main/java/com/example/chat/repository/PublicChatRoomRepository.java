package com.example.chat.repository;

import com.example.chat.model.PublicChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicChatRoomRepository extends JpaRepository<PublicChatRoom, Long> {
}
