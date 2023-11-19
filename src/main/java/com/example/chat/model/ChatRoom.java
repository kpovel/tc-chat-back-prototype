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
public class ChatRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column(length = 1000)
    private String description;

    @Column
    boolean archived = false;

    @Transient
    private List<User> usersChatRoom = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_admin_id")
    private User userAminChatRoom;

    @ElementCollection(targetClass = ChatRoomType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "chat_room_type", joinColumns = @JoinColumn(name = "chat_room_id"))
    @Enumerated(EnumType.STRING)
    private Set<ChatRoomType> chatRoomType = new HashSet<>();


    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtags;

    @JsonManagedReference
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> message = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<UserChatRooms> userChatRooms = new ArrayList<>();
}
