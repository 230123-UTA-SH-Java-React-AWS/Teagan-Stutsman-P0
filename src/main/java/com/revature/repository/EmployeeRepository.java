package com.revature.repository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Employee;

// Repository layer is responsible for interacting w/ database + sending/receiving info from database
public class EmployeeRepository {
    // Store locally on computer for now (employee.json)
    public void Save(Employee employee){
        //Actual implementation
        ObjectMapper mapper = new ObjectMapper();

        try {
            String employeeJSON = mapper.writeValueAsString(employee);

            // Create file if it doesn't already exist
            File employeeFile = new File("./src/main/java/com/revature/repository/employee.json");
            employeeFile.createNewFile();

            // Create FileWriter object and write employee to newline (makes it easier for readline on retrieval)
            FileWriter writer = new FileWriter("./src/main/java/com/revature/repository/employee.json", true);
            writer.write(employeeJSON + "\n");
            writer.close();

        // [{},{},{},{}]

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRegisteredEmployees(){
        ObjectMapper mapper = new ObjectMapper();

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./src/main/java/com/revature/repository/employee.json"));

            while(reader.ready()){
                sb.append(reader.readLine());
                sb.append(",");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        sb.append("]");
        
        return sb.toString().replaceAll(",]$", "]");
    }
    
}
