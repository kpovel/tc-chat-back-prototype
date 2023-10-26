package com.example.chat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "hashtags")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(name = "chat_rooms_hashtags",
    joinColumns = @JoinColumn(name = "hashtag_id"),
    inverseJoinColumns = @JoinColumn(name = "chat_room_id"))
    private List<ChatRoom> chatRoom = new ArrayList<>();
}
