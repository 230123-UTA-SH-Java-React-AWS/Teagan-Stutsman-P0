package com.revature.p0spring.model;

import lombok.Data;

@Data
public class PromoteDto {
    private String managerEmail;
    private String employeeEmail;
    private String newManagerStatus;
}
