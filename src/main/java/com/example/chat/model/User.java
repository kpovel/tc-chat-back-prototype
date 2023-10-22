package com.example.chat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
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
    private String name;

    @Column
    private String userLogin;
    @NotBlank
    @Column
    private String email;
    @Column
    private String locale;
    ;

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

    @OneToOne(mappedBy = "user")
    private Image image;

    public User() {
    }
}
