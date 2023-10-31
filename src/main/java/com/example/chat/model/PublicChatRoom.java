package com.example.chat.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "chat_rooms")
public class PublicChatRoom implements Serializable {

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_admin_id")
    private User userAminChatRoom;

    @ElementCollection(targetClass = ChatRoomType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "chat_room_type", joinColumns = @JoinColumn(name = "chat_room_id"))
    @Enumerated(EnumType.STRING)
    private Set<ChatRoomType> chatRoomType = new HashSet<>();


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_chat_rooms",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> usersChatRoom = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "chat_rooms_hashtags",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<Hashtag> hashtags = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "publicChatRoom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> message = new ArrayList<>();
}
