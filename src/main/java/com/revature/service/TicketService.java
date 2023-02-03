package com.revature.service;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Ticket;
import com.revature.repository.EmployeeRepository;
import com.revature.repository.TicketRepository;

public class TicketService {
    private final TicketRepository ticketRepository = new TicketRepository();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Submits a new ticket
    public void requestReimbursement(String ticketJSON){
        int employeeID = 0;
        float amount = 0.0f;
        String description = "";

        try {

            JsonNode node = objectMapper.readTree(ticketJSON);
            employeeID = node.get("employeeid").getIntValue();
            amount = Float.parseFloat(node.get("amount").asText());
            description = node.get("description").asText();

        } catch (JsonParseException e){
            e.printStackTrace();
        } catch (JsonMappingException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }


        ticketRepository.submitNewTicket(employeeID, amount, description);
    }

    //Returns a JSON String of Tickets the current User created
    public String populateRequestHistory(String usernameJSON){
        ArrayList<Ticket> ticketList = ticketRepository.getAllTickets();
        String username = "";

        // Gets our fields from JSON String
        try {
            JsonNode node = objectMapper.readTree(usernameJSON);
            username = node.get("username").asText();
        }   catch (JsonParseException e){
            e.printStackTrace();
        } catch (JsonMappingException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        EmployeeRepository employeeRepository = new EmployeeRepository();
        // Gets the id from the username
        int employeeID = employeeRepository.getEmployeeID(username);

        // Filters out tickets that don't belong to this employee
        ticketList.removeIf(t -> t.getEmployeeID() != employeeID);

        // Translates Ticket List to JSON String
        return jsonStringify(ticketList);
    }

    // Translates ArrayLists into JSON Strings
    private String jsonStringify(ArrayList<Ticket> listOfTickets){
        String jsonString = "";
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
