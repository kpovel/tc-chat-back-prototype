package com.example.chat.model;

import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

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
    @JsonView(JsonViews.ViewFieldUIID.class)
    private String uuid;

    @Column
    @JsonView(JsonViews.ViewFieldChatName.class)
    private String name;

    @Column(length = 300)
    @JsonView(JsonViews.ViewFieldDescription.class)
    private String description;

    @Column
    boolean archived = false;

    @Transient
    private List<User> usersChatRoom = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_admin_id")
    private User userAminChatRoom;

    @ElementCollection(targetClass = ChatRoomType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "chat_room_type", joinColumns = @JoinColumn(name = "chat_room_id"))
    @Enumerated(EnumType.STRING)
    private Set<ChatRoomType> chatRoomType = new HashSet<>();


    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "hashtag_id")
    @JsonView(JsonViews.ViewFieldHashtag.class)
    private Hashtag hashtag;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonView(JsonViews.ViewFieldMessages.class)
    private List<Message> messages = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<UserChatRoom> userChatRooms = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @JsonView(JsonViews.ViewFieldOther.class)
    private Image image;

    @Transient
    @JsonView(JsonViews.ViewFieldUIID.class)
    private String currentChatUserUIID;

    @PrePersist
    private void init(){
        this.uuid = java.util.UUID.randomUUID().toString();
    }

}
