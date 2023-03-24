package com.revature.p0spring.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class Employee {
    public enum ManagerStatus {EMPLOYEE, MANAGER}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private ManagerStatus managerStatus;
}
