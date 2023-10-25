package com.example.chat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Integer userLimit;

    @ElementCollection(targetClass = ChatType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "type", joinColumns = @JoinColumn(name = "chat_room_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> authority = new HashSet<>();

    @OneToMany
    private List<Hashtag> hashtaghList;
}
