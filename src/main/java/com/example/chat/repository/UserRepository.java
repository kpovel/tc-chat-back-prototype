package com.example.chat.repository;

import com.example.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUserLogin(String login);
    boolean existsByEmail(String email);
    boolean existsByUserLogin(String login);
    Optional<User> findByActivationCode(String code);
}
