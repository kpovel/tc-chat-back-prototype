package com.example.chat.repository;

import com.example.chat.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

//    @Query("SELECT r FROM ChatRoom r INNER JOIN UserChatRooms ur ON r.id = ur.chatRoom WHERE ur.user.id = :userId AND tu.lesson.id = :lessonId AND tu.isRepeatable = true ORDER BY RANDOM() LIMIT 1")
//    Optional<ChatRoom> getChatRoom();

    Optional<ChatRoom> findChatRoomByUuid(String uuid);


}
