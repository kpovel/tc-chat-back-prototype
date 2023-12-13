package com.example.chat.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "hashtags_groups")
@Data
public class HashtagsGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;
    @OneToMany(mappedBy = "hashtagsGroup",cascade = CascadeType.ALL)
    private List<Hashtag> hashtags;
}
