package ru.natlex.taskservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class UserPublicAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String publicId;

    @OneToMany(mappedBy = "userPublicAccount")
    public Set<Task> assignedTasks = new HashSet<>();
}
