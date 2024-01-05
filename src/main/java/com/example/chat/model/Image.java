package com.example.chat.model;

import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "images")
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @JsonView(JsonViews.ViewFieldName.class)
    private String name;

    @JsonBackReference
    @OneToOne(mappedBy = "image")
    private User user;


    public Image() {
    }

}
