package com.example.chat.model;

import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_chat_rooms")
@Data
public class UserChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @JsonView(JsonViews.ViewFieldName.class)
    private String chatName;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    @JsonView(JsonViews.ViewFieldOther.class)
    private ChatRoom chatRoom;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @Transient
    @JsonView(JsonViews.ViewFieldOther.class)
    private Message lastMessage;

}
