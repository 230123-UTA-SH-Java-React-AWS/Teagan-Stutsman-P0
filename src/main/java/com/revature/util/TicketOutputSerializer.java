package com.revature.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import com.revature.model.Ticket;

public class TicketOutputSerializer extends SerializerBase<Ticket> {
    
    public TicketOutputSerializer() {
        this(null);
    }

    public TicketOutputSerializer(Class<Ticket> t) {
        super(t);
    }

    @Override
    public void serialize(Ticket ticket, JsonGenerator jsonGenerator, SerializerProvider serializer) {
        try {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("ticketid", ticket.getTicketID());
            jsonGenerator.writeStringField("username", ticket.getUsername());
            jsonGenerator.writeNumberField("amount", ticket.getAmount());;
            jsonGenerator.writeStringField("reimbursementType", ticket.getReimbursementType().name());
            jsonGenerator.writeStringField("description", ticket.getDescription());
            jsonGenerator.writeStringField("status", ticket.getStatus().name());
            jsonGenerator.writeEndObject();


        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
