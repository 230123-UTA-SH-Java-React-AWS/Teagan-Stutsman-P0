package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.revature.service.EmployeeService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class EmployeeController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // TODO Auto-generated method stub
        String verb = exchange.getRequestMethod();

        switch (verb) {
            case "POST":
                postRequest(exchange);
                break;
            
            default:
                break;

        }
    }

    private void postRequest(HttpExchange exchange) throws IOException{
        
        // InputStream has bytes instead of Strings
        InputStream is = exchange.getRequestBody();

        // Convert InputStream to String
        // StringBuilder is like a mutable version of a String
        StringBuilder textBuilder = new StringBuilder();

        // Convert our binary to letters
        // try_resource block automatically closes the resource in the parenthesis
        try(Reader reader = new BufferedReader(new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;

            // read() gives '-1' once there are no letters left
            while((c = reader.read()) != -1){
                // Adds the letter to your text
                textBuilder.append((char)c);
            }
        }

        exchange.sendResponseHeaders(200, textBuilder.toString().getBytes().length);

        // Don't forget service layer call

        // Creates service layer object
        EmployeeService es = new EmployeeService();

        // Calls save employee method (param: JSON String)
        es.saveEmployee(textBuilder.toString());

        OutputStream os = exchange.getResponseBody();
        os.write(textBuilder.toString().getBytes());
        os.close();

    }
    
}
