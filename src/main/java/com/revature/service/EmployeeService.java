package com.revature.service;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
// import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.revature.model.Employee;
import com.revature.repository.EmployeeRepository;

// Service layer responsible for holding behavior-driven classes
public class EmployeeService {

    public static void requestReimbursement(float amt, String description, int status){
        
    }

    public static String populateRequestHistory(){
        // TODO: generate a requestHistory JSON file via EmpRepo and store like users
        return " ";
    }

    // Recieves JSON String from Controller calls Repo layer method
    public void saveEmployee(String employeeJSON){
        // Create repo layer object
        EmployeeRepository repo = new EmployeeRepository();

        // Jackson for JSON
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Converts JSON employee into Employee object
            
            JsonNode node = mapper.readTree(employeeJSON);
            String username = node.get("username").asText();

            // Helper method to clean up logic a bit
            if(!employeeIsRegistered(repo, username)){
                // Send newEmployee to repository to be stored in the database
                repo.Save(employeeJSON);
            }


        } catch (JsonParseException e) {
              e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean employeeIsRegistered(EmployeeRepository repo, String username){

        // Receive employees from repository
        String allEmployeesJSON = repo.getRegisteredEmployees();
        System.out.println(allEmployeesJSON);


        // Validate that the employee email is unique
        // Generate List from all employees and iterate through them
        ObjectMapper om = new ObjectMapper();

        try {
            List<Employee> listAllEmployees = om.readValue(allEmployeesJSON,  new TypeReference<List<Employee>>(){});
            for(Employee e : listAllEmployees){
                if(e.getUsername().equalsIgnoreCase(username)){
                    System.out.println("Employee already registered...");
                    return true;
                }
            }

            // If none of the employee usernames match, we assume the user is unregistered
            return false;

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // In case something breaks, don't add employee to DB
        return true;
    }
}
