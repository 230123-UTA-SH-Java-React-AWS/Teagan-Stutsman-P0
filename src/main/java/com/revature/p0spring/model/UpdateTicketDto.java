package com.revature.p0spring.model;

import lombok.Data;

@Data
public class UpdateTicketDto {
    private String email;
    private long ticketid;
    private String newStatus;
}
