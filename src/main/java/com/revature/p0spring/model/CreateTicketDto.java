package com.revature.p0spring.model;

import lombok.Data;

@Data
public class CreateTicketDto {
    private String email;
    private float amount;
    private String description;
    private String reimbursementType;
}
