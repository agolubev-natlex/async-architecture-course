package ru.natlex.taskservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Long id;

    @NotNull
    @Column
    public String name;

    @Column
    public String description;

    @Column
    public int withdraw; //int для упрощения

    @Column
    public int award; //int для упрощения

    @Column
    public String userPublicAccountId;

    @Enumerated(EnumType.STRING)
    @Column
    public TaskStatus status = TaskStatus.OPEN;

    @JsonIgnore
    @JoinColumn(name = "userPublicAccountId", referencedColumnName = "publicId", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public UserPublicAccount userPublicAccount;

    public enum TaskStatus {
        OPEN,
        CLOSE
    }
}
