package com.example.chat.repository;

import com.example.chat.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

        @Query(value = "SELECT m FROM Message m WHERE m.chatRoom.id = :chatId AND m.id < :messageId ORDER BY m.id DESC")
        List<Message> findMessagesByChatId(@Param("chatId") Long chatRoomId, @Param("messageId") Long messageId, Pageable pageable);

        @Query("SELECT m FROM Message m WHERE m.chatRoom.id = :id ORDER BY m.id DESC")
        Page<Message> getPageMessage(Pageable pageable, @Param("id")Long chatRoomId);

        void deleteAllByChatRoomId(long chatRoomId);

        @Query("SELECT m FROM Message m WHERE m.chatRoom.id = :chatId AND m.id = (SELECT MAX(m2.id) FROM Message m2 WHERE m2.chatRoom.id = :chatId)")
        Optional<Message> getLastMessageFromChatRoom(@Param("chatId") Long chatRoomId);

}
