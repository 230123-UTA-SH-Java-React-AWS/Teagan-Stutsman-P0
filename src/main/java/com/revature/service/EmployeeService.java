package com.revature.service;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Employee;
import com.revature.repository.EmployeeRepository;

// Service layer responsible for holding behavior-driven classes
public class EmployeeService {

    // Validate user credentials
    public static int login(String user, String pass){
        // log in with email and password
        // Repository search for email

        // Check if pass is correct

        // Return valid 0 or invalid != 0
        return 0;
    }

    public int register(){
        // check if email is already registered - repo search
        boolean alreadyRegistered = false;
        // default role to 'employee'
        int role = 1;
        // register with at least email + password

        // Return valid 0 or invalid != 0
        return 0;
    }

    public static void requestReimbursement(float amt, String description, int status){
        
    }

    public static String populateRequestHistory(){
        return " ";
    }

    // Recieves JSON String from Controller calls Repo layer method
    public void saveEmployee(String employeeJSON){
        // Create repo layer object
        EmployeeRepository repo = new EmployeeRepository();
        
        // Jackson for JSON
        ObjectMapper mapper = new ObjectMapper();

        try {
            // converts JSON employee into Employee object
            JsonNode node = mapper.readTree(employeeJSON);
            String un = node.get("username").asText();
            String pw = node.get("password").asText();
            Employee newEmployee = new Employee(un, pw);
            // Employee newEmployee = mapper.readValue(employeeJSON, Employee.class);

            // TODO: receive employees from repository

            // TODO: validate that the employee email is unique

            // TODO: send newEmployee to repository to be stored in the database
            repo.Save(newEmployee);

        } catch (JsonParseException e) {

            e.printStackTrace();

        } catch (JsonMappingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
