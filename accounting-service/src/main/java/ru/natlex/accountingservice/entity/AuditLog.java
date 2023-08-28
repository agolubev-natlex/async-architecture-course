package ru.natlex.accountingservice.entity;

import jakarta.persistence.*;
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
    public String userPublicId;

    @Column
    public Long debit;

    @Column
    public Long credit;

    @Column
    public Long taskId; //should be string UUID
}
