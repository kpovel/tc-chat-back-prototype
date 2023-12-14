package com.example.chat.model;

import com.example.chat.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
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
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonView(Views.ViewFieldId.class)
    private Long id;

    @Column
    @JsonView(Views.ViewFieldName.class)
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
