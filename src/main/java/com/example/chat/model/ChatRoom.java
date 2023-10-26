package com.example.chat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
    private String name;

    @Column
    private Integer userLimit;

    @Column
    boolean archived = false;

    @ManyToOne
    @JoinColumn(name = "user_admin_id")
    private User userAminChatRoom;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "users_chat_rooms",
    joinColumns = @JoinColumn(name = "chat_room_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> usersChatRoom;

    @ElementCollection(targetClass = ChatRoomType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "chat_room_type", joinColumns = @JoinColumn(name = "chat_room_id"))
    @Enumerated(EnumType.STRING)
    private Set<ChatRoomType> chatRoomType = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(name = "chat_rooms_hashtags",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<Hashtag> hashtags = new ArrayList<>();
}
