package com.example.chat.model;

import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
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
    private Long id;

    @Column
    @JsonView(JsonViews.ViewFieldUUID.class)
    private String uuid;

    @Column
    @JsonView(JsonViews.ViewFieldName.class)
    private String name;

    @Column
    @JsonView(JsonViews.ViewFieldUserLogin.class)
    private String userLogin;

    @Column(columnDefinition = "text", length = 450)
    private String about = "A little bit about me";

    @Column
    @JsonView(JsonViews.ViewFieldUserEmail.class)
    private String email;

    @Transient
    private String locale;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    @JsonView(JsonViews.ViewFieldUserImage.class)
    private Image image;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserChatRoom> userChatRooms = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "userAminChatRoom", cascade = CascadeType.ALL)
    private List<ChatRoom> userChatRoomsAdmin = new ArrayList<>();


    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "users_hashtags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<Hashtag> hashtags = new ArrayList<>();


    @Column(name = "date_last_visit")
    @JsonView(JsonViews.ViewFieldUserDateLastVisit.class)
    private LocalDateTime dateLastVisit;

    @Column(name = "date_of_created", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(JsonViews.ViewFieldDateOfCreated.class)
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init(){
        this.dateOfCreated = LocalDateTime.now();
        this.uuid = java.util.UUID.randomUUID().toString();
    }


    public User() {
    }

    public User(String email) {
        this.email = email;
    }
}
