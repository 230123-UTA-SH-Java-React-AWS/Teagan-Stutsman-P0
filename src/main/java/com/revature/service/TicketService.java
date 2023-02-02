package com.revature.service;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Ticket;
import com.revature.repository.TicketRepository;

public class TicketService {
    // Submits a new ticket
    public void requestReimbursement(String ticketJSON){
        // TODO: check to make sure this works
        TicketRepository repo = new TicketRepository();

        ObjectMapper om = new ObjectMapper();

        try {
            JsonNode node = om.readTree(ticketJSON);
            float amount = Float.parseFloat(node.get("amount").asText());
            String description = node.get("description").asText();

            repo.submitNewTicket(amount, description);
        } catch (JsonParseException e){
            e.printStackTrace();
        } catch (JsonMappingException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String populateRequestHistory(){
        // Returns tickets this user created in json string form
        // TODO: check to make sure this works as well
        TicketRepository repo = new TicketRepository();
        ArrayList<Ticket> listAllTickets = repo.getAllTickets();

        ObjectMapper om = new ObjectMapper();

        String jsonString = "";

        try {
            jsonString = om.writeValueAsString(listAllTickets);
        } catch (JsonMappingException e){
            e.printStackTrace();
        } catch (JsonGenerationException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return jsonString;
    }
}
