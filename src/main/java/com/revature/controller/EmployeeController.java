package com.revature.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.revature.service.EmployeeService;


public class EmployeeController implements HttpHandler {
    EmployeeService employeeService = new EmployeeService();

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
                System.out.println("Bruh wtf...");
                break;

        }
    }

    // Will be used to login user
    private void getRequest(HttpExchange exchange) throws IOException{
        String httpRequestBody = getHttpRequestBody(exchange);
        String outgoingMessage = "";

        int logInSuccess = employeeService.loginEmployee(httpRequestBody);

        switch (logInSuccess) {
            case 0:
                outgoingMessage = "You're Logged In! Welcome"; // Bonus if i can get the username here haha
                exchange.sendResponseHeaders(200, outgoingMessage.getBytes().length);
                break;
            case 1:
                outgoingMessage = "Invalid Password";
                exchange.sendResponseHeaders(401, outgoingMessage.getBytes().length);
                break;
            case 2:
                outgoingMessage = "Error - Username Not Recognized";
                exchange.sendResponseHeaders(401, outgoingMessage.getBytes().length);
                break;
            default:
                break;
        }

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(outgoingMessage.getBytes());
        outputStream.close();
    }

    // Creates a new user
    private void postRequest(HttpExchange exchange) throws IOException{
        String httpRequestBody = getHttpRequestBody(exchange);
        String outgoingMessage = "";

        // Calls save employee method (param: JSON String)
        boolean registrationSuccess = employeeService.registerEmployee(httpRequestBody);

        if(registrationSuccess){
            outgoingMessage = "You're Registered! Welcome";
            exchange.sendResponseHeaders(200, outgoingMessage.getBytes().length);
        } else {
            outgoingMessage = "Username Already Taken";
            exchange.sendResponseHeaders(409, outgoingMessage.getBytes().length);
        }

        OutputStream os = exchange.getResponseBody();
        os.write(outgoingMessage.getBytes());
        os.close();

    }

    // Will be used to update employee passwords
    private void putRequest(HttpExchange exchange) throws IOException{
        String httpRequestBody = getHttpRequestBody(exchange);
        String outgoingMessage = "";

        boolean passwordChangeSuccess = employeeService.changeEmployeePassword(httpRequestBody);

        if(passwordChangeSuccess){
            outgoingMessage = "Password Successfully Changed";
            exchange.sendResponseHeaders(409, outgoingMessage.getBytes().length);
        } else {
            outgoingMessage = "Password Change Failed";
            exchange.sendResponseHeaders(409, outgoingMessage.getBytes().length);
        }

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(outgoingMessage.getBytes());
        outputStream.close();
    }

    // Will be using this request to remove an existing employee
    private void deleteRequest(HttpExchange exchange) throws IOException{
        System.out.println("No functionality for delete yet...");
    }
    
    // Helper method to translate RequestBody from bytes to String
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
