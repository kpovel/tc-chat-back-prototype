package com.example.chat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    @JsonView(Views.ViewFieldId.class)
    private Long id;

    @Column
    @JsonView(Views.ViewFieldName.class)
    private String name;

    @Column
    @JsonView(Views.ViewFieldUserLogin.class)
    private String userLogin;

    @Column(length = 300)
    private String description;

    @NotBlank
    @Column
    @JsonView(Views.ViewFieldUserEmail.class)
    private String email;

    @Column
    private String locale;

    @NotBlank
    @Column(length = 100)
    private String password;

    @Column
    private String activationCode;

    @Column
    private boolean enable;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> authority = new HashSet<>();

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;


    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_hashtags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<Hashtag> hashtags;


    @Column(name = "date_last_visit")
    @JsonView(Views.ViewFieldUserDateLastVisit.class)
    private LocalDateTime dateLastVisit;

    @Column(name = "date_of_created", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.ViewFieldUserDateOfCreated.class)
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
