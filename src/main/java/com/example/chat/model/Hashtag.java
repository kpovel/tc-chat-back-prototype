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

    public Hashtag(String engName, String ukrName) {
        this.engName = engName;
        this.ukrName = ukrName;
    }
}
