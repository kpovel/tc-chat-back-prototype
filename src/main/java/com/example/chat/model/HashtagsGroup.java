package com.example.chat.model;

import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hashtags_groups")
@Data
public class HashtagsGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
//    @JsonView(Views.ViewFieldId.class)
    private Long id;

    @Column
    private String locale;

    @Column
    @JsonView(JsonViews.ViewFieldName.class)
    private String name;


    @OneToMany(mappedBy = "hashtagsGroup",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonView(JsonViews.ViewFieldOther.class)
    private List<Hashtag> hashtags = new ArrayList<>();


    @JsonIgnore
    @OneToOne(mappedBy = "ukHashtagsGroup", fetch = FetchType.LAZY)
    private HashtagsGroup engHashtagsGroup;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "uk_hashtag_group_id", referencedColumnName = "id")
    private HashtagsGroup ukHashtagsGroup;

    public HashtagsGroup() {
    }
    public HashtagsGroup(String name, String locale) {
        this.name = name;
        this.locale = locale;
    }

}
