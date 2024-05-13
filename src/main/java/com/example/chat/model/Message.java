package com.example.chat.model;

import com.example.chat.utils.CustomMessageSerializer;
import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "messages")
@JsonSerialize(using = CustomMessageSerializer.class)
public class Message {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonView(JsonViews.ViewFieldUUID.class)
    private String uuid;

    @Column(columnDefinition = "text")
    @JsonView(JsonViews.ViewFieldMessageContent.class)
    private String content;

    @Column
    private boolean edited = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonView(JsonViews.ViewFieldUser.class)
    private User user;

    @Column(name = "date_of_created")
    @JsonView(JsonViews.ViewFieldDateOfCreated.class)
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
        uuid = java.util.UUID.randomUUID().toString();
    }

    public Message() {
    }

}
