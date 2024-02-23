package com.example.chat.model;

import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "hashtags")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonView(JsonViews.ViewFieldUIID.class)
    private Long id;

    @Column
    @JsonView(JsonViews.ViewFieldName.class)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private HashtagsGroup hashtagsGroup;

    public Hashtag() {
    }

    public Hashtag(String name, HashtagsGroup hashtagsGroup) {
        this.name = name;
        this.hashtagsGroup = hashtagsGroup;
    }
}
