package ru.natlex.authservice.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(updatable = false)
    private String publicId = UUID.randomUUID().toString();

    @Column
    private String username;

    @Column
    @JsonProperty("password")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    public enum Role {
        ADMIN,
        MANAGER,
        POPUG
    }
}
