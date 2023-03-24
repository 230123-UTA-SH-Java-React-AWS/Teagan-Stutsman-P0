package com.revature.p0spring.model;

import lombok.Data;

@Data
public class GetTicketDto {
    private String email;
    private String ticketStatus;
    private String reimbursementType;
}
