package com.example.chat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(name = "chat_rooms_hashtags",
    joinColumns = @JoinColumn(name = "hashtag_id"),
    inverseJoinColumns = @JoinColumn(name = "chat_room_id"))
    private List<ChatRoom> chatRoom;
}
