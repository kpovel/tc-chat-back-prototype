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
    private String engGroupName;

    @Column
    private String ukrGroupName;

    @ManyToOne
    @JoinColumn
    private HashtagsGroup hashtagsGroup;

    public Hashtag(String engName, String ukrName, String engGroupName, String ukrGroupName) {
        this.engName = engName;
        this.ukrName = ukrName;
        this.engGroupName = engGroupName;
        this.ukrGroupName = ukrGroupName;
    }
}
