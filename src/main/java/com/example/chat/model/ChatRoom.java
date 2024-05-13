package com.example.chat.model;

import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    @JsonView(JsonViews.ViewFieldUUID.class)
    private String uuid;

    @Column
    @JsonView(JsonViews.ViewFieldChatName.class)
    private String name;

    @Column(length = 300)
    @JsonView(JsonViews.ViewFieldDescription.class)
    private String description;

    @Column
    boolean archived = false;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_admin_id")
    private User userAminChatRoom;

    @Column(name = "date_of_created")
    @JsonView(JsonViews.ViewFieldDateOfCreated.class)
    private LocalDateTime dateOfCreated;

    @ElementCollection(targetClass = ChatRoomType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "chat_room_type", joinColumns = @JoinColumn(name = "chat_room_id"))
    @Enumerated(EnumType.STRING)
    @JsonView(JsonViews.ViewFieldChatRoomType.class)
    private Set<ChatRoomType> chatRoomType = new HashSet<>();


    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "hashtag_id")
    @JsonView(JsonViews.ViewFieldHashtag.class)
    private Hashtag hashtag;

    @JsonManagedReference
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<UserChatRoom> userChatRooms = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @JsonView(JsonViews.ViewFieldOther.class)
    private Image image;

    @Transient
    private List<User> usersChatRoom = new ArrayList<>();

    @Transient
    @JsonView(JsonViews.ViewFieldChatIsAdmin.class)
    private boolean isAdmin;

    @Transient
    @JsonView(JsonViews.ViewFieldOther.class)
    private boolean isJoin;

    @Transient
    @JsonView(JsonViews.ViewFieldUUID.class)
    private String currentChatUserUUID;

    @Transient
    @JsonView(JsonViews.ViewFieldMessages.class)
    private List<Message> messages = new ArrayList<>();

    @PrePersist
    private void init(){
        this.dateOfCreated = LocalDateTime.now();
        this.uuid = java.util.UUID.randomUUID().toString();
    }

}
