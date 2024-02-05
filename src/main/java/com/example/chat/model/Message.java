package com.example.chat.model;

import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @JsonView(JsonViews.ViewFieldUiid.class)
    private String uuid;

    @Column
    @JsonView(JsonViews.ViewFieldMessageContent.class)
    private String content;

    @Column
    private boolean edited = false;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_of_created")
    @JsonView(JsonViews.ViewFieldDateOfCreated.class)
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init(){
        this.dateOfCreated = LocalDateTime.now();
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    public Message() {
    }



}
