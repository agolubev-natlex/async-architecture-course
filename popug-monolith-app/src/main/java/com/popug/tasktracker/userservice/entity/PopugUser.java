package com.popug.tasktracker.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "popug_user")
@NoArgsConstructor @Getter @Setter
public class PopugUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID publicId;

    @Column(unique = true)
    private String username;

    private String password;

    private String name;
}
