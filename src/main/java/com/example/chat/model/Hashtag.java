package com.example.chat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "hashtags")
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String engName;

    @Column
    private String ukrName;


    @Column
    private String globalCategory;

//    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
//    @JoinTable(name = "chat_rooms_hashtags",
//    joinColumns = @JoinColumn(name = "hashtag_id"),
//    inverseJoinColumns = @JoinColumn(name = "chat_room_id"))
//    private List<ChatRoom> chatRoom = new ArrayList<>();


    public Hashtag(String engName, String ukrName, String globalCategory) {
        this.engName = engName;
        this.ukrName = ukrName;
        this.globalCategory = globalCategory;
    }
}
