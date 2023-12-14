package com.example.chat.repository;

import com.example.chat.model.HashtagsGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagGroupRepository extends JpaRepository<HashtagsGroup, Long> {
    Optional<List<HashtagsGroup>> findAllByLocale(String locale);
}
