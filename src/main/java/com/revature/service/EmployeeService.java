package com.revature.service;

import java.io.IOException;
import java.util.HashSet;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Employee;
import com.revature.repository.EmployeeRepository;

// Service layer responsible for holding behavior-driven classes
public class EmployeeService {

    public static void requestReimbursement(float amt, String description, int status){
        
    }

    public static String populateRequestHistory(){
        // TODO: create a requestHistory database and store like users
        return " ";
    }






    // Recieves JSON String from Controller calls Repo layer method
    public void registerEmployee(String employeeJSON){
        // Create repo layer object
        EmployeeRepository repo = new EmployeeRepository();
        HashSet<String> listAllEmployees = repo.getRegisteredEmployees();
        String username = "";
        String password = "";

        // Jackson for JSON
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Converts JSON employee into Employee object
            JsonNode node = mapper.readTree(employeeJSON);
            username = node.get("username").asText();
            password = node.get("password").asText();

            if(!listAllEmployees.contains(username)){
                // Send newEmployee to repository to be stored in the database
                Employee employee = new Employee(username);
                repo.registerNewEmployee(employee, password);
            } else {
                System.out.println("--- employee already registered --- new employee not created ---");
            }
            
        } catch (JsonParseException e) {
              e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
