package com.example.chat.repository;

import com.example.chat.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

        @Query("SELECT m FROM Message m WHERE m.chatRoom.id = :id ORDER BY m.id ASC")
        Page<Message> findPageMessage(Pageable pageable, @Param("id")Long chatRoomId);

}
