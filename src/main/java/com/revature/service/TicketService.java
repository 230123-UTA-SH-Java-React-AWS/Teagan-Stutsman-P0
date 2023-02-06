package com.revature.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

import com.revature.model.Ticket;
import com.revature.model.User;
import com.revature.model.User.ManagerStatus;
import com.revature.repository.EmployeeRepository;
import com.revature.repository.TicketRepository;
import com.revature.util.TicketOutputSerializer;

public class TicketService {
    private final TicketRepository ticketRepository = new TicketRepository();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Submits a new ticket
    public int requestReimbursement(String ticketJSON){
        String username, description;
        float amount;

        try {
            
            JsonNode node = objectMapper.readTree(ticketJSON);
            username = node.get("username").asText();
            amount = Float.parseFloat(node.get("amount").asText());
            description = node.get("description").asText();

        } catch (NumberFormatException e){
            return 2;
        } catch (JsonParseException e){
            e.printStackTrace();
            return 3;
        } catch (JsonMappingException e){
            e.printStackTrace();
            return 3;
        } catch (IOException e){
            e.printStackTrace();
            return 3;
        }

        EmployeeRepository employeeRepository = new EmployeeRepository();

        //Check to make sure this user exists
        HashSet<String> allEmployees = employeeRepository.getRegisteredEmployees();
        if(!allEmployees.contains(username)){
            return 4; // Username not recognized
        }

        User user = employeeRepository.getEmployee(username);

        if(description.equals("")){
            return 1;
        } else if (amount < 0.01f){
            return 2;
        }

        ticketRepository.submitNewTicket(user.getEmployeeID(), amount, description);
        return 0;
    }

    //Returns a JSON String of Tickets the current User created
    public String populateRequestHistory(String usernameJSON){
        ArrayList<Ticket> ticketList = ticketRepository.getAllTickets();
        String username = "";
        Ticket.Status ticketStatus = Ticket.Status.PENDING;
        boolean filterByStatus = false;

        // Gets our fields from JSON String
        try {
            JsonNode node = objectMapper.readTree(usernameJSON);
            username = node.get("username").asText();

            if(node.findValue("ticketStatus") != null){
                filterByStatus = true;
                ticketStatus = Ticket.Status.valueOf(node.get("ticketStatus").asText());
            }
            
        }   catch (JsonParseException e){
            e.printStackTrace();
        } catch (JsonMappingException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        EmployeeRepository employeeRepository = new EmployeeRepository();
        // Gets the id from the username
        User user = employeeRepository.getEmployee(username);

        if(!user.getManagerStatus().equals(ManagerStatus.MANAGER)){
            // Filters out tickets that don't belong to this employee
            ticketList.removeIf(t -> t.getEmployeeID() != user.getEmployeeID());
        } // else - Print all tickets

        if(filterByStatus){
            // Filters out status that aren't requested
            final Ticket.Status compareStatus = ticketStatus; // compare value needs to be final
            ticketList.removeIf(t -> !compareStatus.equals(t.getStatus()));
        }

        // Add username to ticket
        HashMap<Integer, String> employeesByID = employeeRepository.getEmployeesByID();
        ticketList.forEach(t -> t.setUsername(employeesByID.get(t.getEmployeeID())));

        // Translates Ticket List to JSON String
        return jsonStringify(ticketList);
    }

    public int updateRequestStatus(String requestJSON){
        String username;
        int ticketID;
        Ticket.Status status;

        try {
            JsonNode node = objectMapper.readTree(requestJSON);
            username = node.get("username").asText();
            ticketID = node.get("ticketid").asInt();
            status = Ticket.Status.valueOf(node.get("newStatus").asText());

        } catch (JsonParseException e){
            e.printStackTrace();
            return 3;
        } catch (JsonMappingException e){
            e.printStackTrace();
            return 3;
        } catch (IOException e){
            e.printStackTrace();
            return 3; // 3 - Exception
        }
        
        EmployeeRepository employeeRepository = new EmployeeRepository();
        User user = employeeRepository.getEmployee(username);

        Ticket ticket = ticketRepository.getOneTicket(ticketID);

        boolean userIsManager = user.getManagerStatus().equals(ManagerStatus.MANAGER);
        boolean ticketIsPending = ticket.getStatus().equals(Ticket.Status.PENDING);

        if(userIsManager && ticketIsPending) {
            ticketRepository.updateTicketStatus(ticketID, status);
            return 0; // Status changed :)
        } else if (userIsManager) {
            // ticket status cannot be changed after approval/denial(1)
            return 1;
        }// else - not a manager(2) and cannot update ticket status

        return 2;
    }

    // Translates ArrayLists into JSON Strings
    private String jsonStringify(ArrayList<Ticket> listOfTickets){
        String jsonString = "";

        // Implements Custom Ticket Serializer from util
        SimpleModule module = new SimpleModule("TicketOutputSerializer", new Version(1, 0, 0, null));
        module.addSerializer(Ticket.class, new TicketOutputSerializer());
        objectMapper.registerModule(module);

        try {
            jsonString = objectMapper.writeValueAsString(listOfTickets);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
