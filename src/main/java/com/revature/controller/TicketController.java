package com.revature.controller;

import java.io.IOException;
import java.io.OutputStream;

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
                System.out.println("No functionality for put yet...");
                //putRequest(exchange);
                break;
            
            case "DELETE":
                System.out.println("No functionality for delete yet...");
                //deleteRequest(exchange);
                break;
            
            default:
                System.out.println("Bruh wtf...");
                break;

        }
    }
    
    private void getRequest(HttpExchange exchange) throws IOException {
        String jsonCurrentList = ts.populateRequestHistory();

        exchange.sendResponseHeaders(200, jsonCurrentList.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(jsonCurrentList.getBytes());
        os.close();
    }

    private void postRequest(HttpExchange exchange) throws IOException {

    }
}
