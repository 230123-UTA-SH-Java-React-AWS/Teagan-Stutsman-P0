package com.revature.p0spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.p0spring.model.CreateTicketDto;
import com.revature.p0spring.model.GetTicketDto;
import com.revature.p0spring.model.Ticket;
import com.revature.p0spring.model.UpdateTicketDto;
import com.revature.p0spring.service.TicketService;

@RestController
@RequestMapping("tickets")
public class TicketController {

    private TicketService ticketService;
    
    @Autowired
    public TicketController( TicketService ticketService ){
        this.ticketService = ticketService;
    }

    @GetMapping("/getTickets")
    public ResponseEntity<List<Ticket>> getAllTickets(@RequestBody GetTicketDto getTicketDto){
        return ticketService.getAllTickets(getTicketDto);
    }

    @PostMapping("/createTicket")
    public ResponseEntity<String> createTicket(@RequestBody CreateTicketDto createTicketDto){
        return ticketService.createTicket(createTicketDto);
    }

    @PutMapping("/updateRequestStatus")
    public ResponseEntity<String> updateRequestStatus(@RequestBody UpdateTicketDto updateTicketDto){
        return ticketService.updateRequestStatus(updateTicketDto);
    }
}
