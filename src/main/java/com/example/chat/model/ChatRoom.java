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
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Integer userLimit;

    @Column
    boolean archived = false;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userAminChatRoom;

    @ManyToMany
    @JoinTable(name = "users_chat_rooms",
    joinColumns = @JoinColumn(name = "chat_room_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> usersChatRoom;
    @ElementCollection(targetClass = ChatType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "type", joinColumns = @JoinColumn(name = "chat_room_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> authority = new HashSet<>();

    @OneToMany
    private List<Hashtag> hashtaghList;
}
