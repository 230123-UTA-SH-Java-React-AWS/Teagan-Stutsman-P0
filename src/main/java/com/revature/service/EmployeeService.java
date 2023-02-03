package com.revature.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Employee;
import com.revature.repository.EmployeeRepository;

// Service layer responsible for holding behavior-driven classes
public class EmployeeService {
    // Create repo layer object
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    // Jackson for JSON
    private final ObjectMapper objectMapper = new ObjectMapper();



    // Recieves JSON String from Controller calls Repo layer method
    public boolean registerEmployee(String employeeJSON){
        HashSet<String> listAllEmployees = employeeRepository.getRegisteredEmployees();
        String username = "";
        String password = "";

        try {
            // Converts JSON employee into Employee object
            JsonNode node = objectMapper.readTree(employeeJSON);
            username = node.get("username").asText();
            password = node.get("password").asText();
            
        } catch (JsonParseException e) {
              e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(!listAllEmployees.contains(username)){
            // Send newEmployee to repository to be stored in the database
            Employee employee = new Employee(username);
            employeeRepository.registerNewEmployee(employee, password);
            return true;
        }
            
        System.out.println("--- employee already registered --- new employee not created ---");
        return false;
    }


    // Returns true if the password matches the username
    public int loginEmployee(String employeeJSON){
        HashMap<String, String> employeePasswords = employeeRepository.getEmployeePasswords();
        String username = "";
        String password = "";
        
        try {
            // Converts JSON employee into Employee object
            JsonNode node = objectMapper.readTree(employeeJSON);
            username = node.get("username").asText();
            password = node.get("password").asText();
            
        } catch (JsonParseException e) {
              e.printStackTrace();
              return 3; // Exception Failure
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return 3; // Exception Failure
        } catch (IOException e) {
            e.printStackTrace();
            return 3; // Exception Failure
        }

        if(!employeePasswords.containsKey(username)){
            return 2; // No username registered failure
        }

        if(employeePasswords.get(username).equals(password)){
            return 0;
        }

        return 1; // Password incorrect failure
    }


    // Returns true if the old password was correct
    public boolean changeEmployeePassword(String employeeJSON){
        if(loginEmployee(employeeJSON) == 0){
            String newPassword = "";
            String username = "";

            try {
                // Converts JSON employee into Employee object
                JsonNode node = objectMapper.readTree(employeeJSON);
                newPassword = node.get("newPassword").asText();
                username = node.get("username").asText();
                
            } catch (JsonParseException e) {
                  e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            employeeRepository.changeEmployeePassword(username, newPassword);
            return true;
        }
        return false;
    }
}
