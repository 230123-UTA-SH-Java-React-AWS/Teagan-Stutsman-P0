package com.revature.p0spring.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class Ticket {
    public enum Status {PENDING, APPROVED, DENIED}
    public enum ReimbursementType {OTHER, TRAVEL, LODGING, FOOD}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private long employeeID;
    private float amount;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private ReimbursementType reimbursementType;
}
