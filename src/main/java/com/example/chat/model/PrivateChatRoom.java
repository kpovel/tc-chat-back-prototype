package com.example.chat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "private_chat_rooms")
public class PrivateChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private  Long id;

    @OneToMany(mappedBy = "privateChatRoom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> message = new ArrayList<>();



}
