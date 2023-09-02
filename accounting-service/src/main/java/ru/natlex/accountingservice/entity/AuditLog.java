package ru.natlex.accountingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Long id;

    @Column
    public String operation; //can be enum?

    @Column
    @NotNull
    public String userPublicId;

    @Column
    public Long debit;

    @Column
    public Long credit;

    @Column
    public Long taskId; //should be string UUID
}
