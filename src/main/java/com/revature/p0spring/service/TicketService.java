package com.revature.p0spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.p0spring.model.CreateTicketDto;
import com.revature.p0spring.model.Employee;
import com.revature.p0spring.model.GetTicketDto;
import com.revature.p0spring.model.Ticket;
import com.revature.p0spring.model.UpdateTicketDto;
import com.revature.p0spring.model.Employee.ManagerStatus;
import com.revature.p0spring.model.Ticket.ReimbursementType;
import com.revature.p0spring.model.Ticket.Status;
import com.revature.p0spring.repository.EmployeeRepository;
import com.revature.p0spring.repository.TicketRepository;

@Service
public class TicketService {
    
    private TicketRepository ticketRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, EmployeeRepository employeeRepository){
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;
    }


    // ===================================================================================================
    // ---------------------------------- RETRIEVE TICKETS -----------------------------------------------
    // ===================================================================================================
    public ResponseEntity<List<Ticket>> getAllTickets(GetTicketDto getTicketDto){
        String ticketStatus = getTicketDto.getTicketStatus();
        String reimbursementType = getTicketDto.getReimbursementType();
        System.out.println(reimbursementType);
        if(!ticketStatus.equals("PENDING") && !ticketStatus.equals("DENIED") && !ticketStatus.equals("APPROVED") && !ticketStatus.equals("")){
            System.out.println("Invalid status");
            return ResponseEntity.status(400).body(null);
        }

        boolean employeeExists = employeeRepository.existsByEmail(getTicketDto.getEmail());

        if(!employeeExists){
            System.out.println("Employee doesn't exist");
            return ResponseEntity.status(400).body(null);
        }

        Optional<Employee> requestingEmployee = employeeRepository.findByEmail(getTicketDto.getEmail());

        // Gets all pending tickets for managers
        if(requestingEmployee.get().getManagerStatus().equals(ManagerStatus.MANAGER)){
            System.out.println("Is a manager");
            List<Ticket> ticketList;
            if(ticketStatus.equals("")){
                ticketList = ticketRepository.findAllOrderByIdDesc();
            } else {
                ticketList = ticketRepository.findAllByStatusOrderByIdDesc(Status.valueOf(ticketStatus));
            }
            
            return ResponseEntity.status(200).body(ticketList);
        }

        List<Ticket> ticketList;
        boolean reimbursementTypeEmpty = reimbursementType.equals("") || reimbursementType.equals(null);
        System.out.println(reimbursementTypeEmpty);

        if(ticketStatus.equals("") && reimbursementTypeEmpty){ 
            // No status or type to filter
            ticketList = ticketRepository.findAllByEmployeeID(requestingEmployee.get().getId());

        } else if(reimbursementTypeEmpty) { 
            // Filter by status not type
            ticketList = ticketRepository.findAllByEmployeeIDAndStatus(requestingEmployee.get().getId(), Status.valueOf(ticketStatus));
        
        } else if(ticketStatus.equals("")) { 
            // Filter by type not status
            ticketList = ticketRepository.findAllByEmployeeIDAndType(requestingEmployee.get().getId(), ReimbursementType.valueOf(reimbursementType));
        
        } else { 
            // Filter by status and type
            ticketList = ticketRepository.findAllByEmployeeIDAndStatusAndType(requestingEmployee.get().getId(), Status.valueOf(ticketStatus), ReimbursementType.valueOf(reimbursementType));
        }

        return ResponseEntity.status(200).body(ticketList);
    }

    // ===================================================================================================
    // ---------------------------------- CREATE TICKETS -------------------------------------------------
    // ===================================================================================================
    public ResponseEntity<String> createTicket(CreateTicketDto createTicketDto){
        if(createTicketDto.getDescription().equals("")){
            return ResponseEntity.status(403).body("Missing valid description");
        }

        if(createTicketDto.getAmount() < 0.01f){
            return ResponseEntity.status(403).body("Amount must be greater than $0.00");
        }

        Optional<Employee> submittingEmployee = employeeRepository.findByEmail(createTicketDto.getEmail());

        if(!submittingEmployee.isPresent()){
            return ResponseEntity.status(403).body("Email not recognized");
        }

        Ticket newTicket = new Ticket();
        newTicket.setEmail(createTicketDto.getEmail());
        newTicket.setEmployeeID(submittingEmployee.get().getId());
        newTicket.setAmount(createTicketDto.getAmount());
        newTicket.setDescription(createTicketDto.getDescription());
        newTicket.setStatus(Status.PENDING);

        if(createTicketDto.getReimbursementType().equals("")){
            newTicket.setReimbursementType(ReimbursementType.OTHER);
        } else {
            newTicket.setReimbursementType(ReimbursementType.valueOf(createTicketDto.getReimbursementType()));
        }
        
        ticketRepository.save(newTicket);

        return ResponseEntity.status(200).body("Ticket submitted successfully");
    }


    // ===================================================================================================
    // ---------------------------------- UPDATE TICKETS -------------------------------------------------
    // ===================================================================================================
    @Transactional
    public ResponseEntity<String> updateRequestStatus(UpdateTicketDto updateTicketDto){
        String newStatus = updateTicketDto.getNewStatus();
        if(!newStatus.equals("DENIED") && !newStatus.equals("APPROVED")){
            return ResponseEntity.status(400).body("Invalid ticket status");
        }

        Optional<Employee> updatingEmployee = employeeRepository.findByEmail(updateTicketDto.getEmail());

        if(!updatingEmployee.isPresent()){
            return ResponseEntity.status(403).body("Email does not exist");
        }

        Optional<Ticket> ticketToUpdate = ticketRepository.findById(updateTicketDto.getTicketid());

        if(!ticketToUpdate.isPresent()){
            return ResponseEntity.status(404).body("Ticket id does not exist");
        }

        if(updatingEmployee.get().getManagerStatus().equals(ManagerStatus.MANAGER) && ticketToUpdate.get().getStatus().equals(Status.PENDING)){
            ticketRepository.updateTicketStatusById(Status.valueOf(updateTicketDto.getNewStatus()), updateTicketDto.getTicketid());
            return ResponseEntity.status(200).body("Ticket successfully processed");
        } else if (updatingEmployee.get().getManagerStatus().equals(ManagerStatus.MANAGER)){
            return ResponseEntity.status(400).body("Ticket status cannot be changed after approval/denial");
        } else {
            return ResponseEntity.status(400).body("Employee not a manager");
        }
    }
}
