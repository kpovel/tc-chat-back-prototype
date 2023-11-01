package com.example.chat.repository;

import com.example.chat.model.ChatTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatThemeRepository extends JpaRepository<ChatTheme, Long> {
}
