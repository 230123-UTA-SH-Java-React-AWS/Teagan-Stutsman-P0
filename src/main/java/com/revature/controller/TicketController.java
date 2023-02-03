package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.revature.service.TicketService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TicketController implements HttpHandler {
    private final TicketService ts = new TicketService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String verb = exchange.getRequestMethod();

        switch (verb) {
            case "GET":
                getRequest(exchange);
                break;

            case "POST":
                postRequest(exchange);
                break;
            
            case "PUT":
                putRequest(exchange);
                break;
            
            case "DELETE":
                deleteRequest(exchange);
                break;
            
            default:
                defaultRequest(exchange);
                System.out.println("Bruh wtf...");
                break;

        }
    }
    
    private void getRequest(HttpExchange exchange) throws IOException {
        String httpRequestBody = getHttpRequestBody(exchange);

        String jsonGetTicketsForEmployee = ts.populateRequestHistory(httpRequestBody);

        exchange.sendResponseHeaders(200, jsonGetTicketsForEmployee.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(jsonGetTicketsForEmployee.getBytes());
        os.close();
    }

    private void postRequest(HttpExchange exchange) throws IOException {   
        String response = "";
        String httpRequestBody = getHttpRequestBody(exchange);
        boolean ticketSubmittedSuccess = ts.requestReimbursement(httpRequestBody);

        if(ticketSubmittedSuccess){
            response = "Reimbursement Ticket Submitted!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
        } else {
            response = "Ticket Invalid - Could Not Be Submitted";
            exchange.sendResponseHeaders(400, response.getBytes().length);
        }

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }


    private void putRequest(HttpExchange exchange) throws IOException {
        // Default is no funcionality unless I add it in later
        String response = "No functionality for put yet...";
        exchange.sendResponseHeaders(500, response.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }


    private void deleteRequest(HttpExchange exchange) throws IOException {
        String response = "No functionality for delete yet...";
        exchange.sendResponseHeaders(500, response.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    private void defaultRequest(HttpExchange exchange) throws IOException {
        String response = "Error processing request";
        exchange.sendResponseHeaders(500, response.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    private String getHttpRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        StringBuilder stringBuilder = new StringBuilder();

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;

            // read() gives '-1' once there are no letters left
            while((c = reader.read()) != -1){
                // Adds the letter to your text
                stringBuilder.append((char)c);
            }
        }

        return stringBuilder.toString();
    }
}
