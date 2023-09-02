package ru.natlex.authservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @JsonIgnore
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    public enum Role {
        ADMIN,
        MANAGER,
        ACCOUNTANT,
        POPUG
    }
}
