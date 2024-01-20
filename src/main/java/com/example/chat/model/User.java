package com.example.chat.model;

import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonView(JsonViews.ViewFieldId.class)
    private Long id;

    @Column
    @JsonView(JsonViews.ViewFieldName.class)
    private String name;

    @Column
    @JsonView(JsonViews.ViewFieldUserLogin.class)
    private String userLogin;

    @Column(columnDefinition = "text", length = 450)
    private String about = "A little bit about me";

    @NotBlank
    @Column
    @JsonView(JsonViews.ViewFieldUserEmail.class)
    private String email;

    @Column
    private String locale;

    @NotBlank
    @Column(length = 100)
    private String password;

    @Column
    private String uniqueServiceCode;

    @Column
    private boolean enable = false;

    @Column
    @JsonView(JsonViews.ViewFieldOther.class)
    private boolean onboardingEnd = false;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> authority = new HashSet<>();

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    @JsonView(JsonViews.ViewFieldOther.class)
    private Image image;


    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_hashtags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<Hashtag> hashtags = new ArrayList<>();


    @Column(name = "date_last_visit")
    @JsonView(JsonViews.ViewFieldUserDateLastVisit.class)
    private LocalDateTime dateLastVisit;

    @Column(name = "date_of_created", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(JsonViews.ViewFieldUserDateOfCreated.class)
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }


    public User() {
    }

    public User(String email) {
        this.email = email;
    }
}
