package com.revature.repository;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Employee;

// Repository layer is responsible for interacting w/ database + sending/receiving info from database
public class EmployeeRepository {
    // Store locally on computer
    public void Save(Employee employee){
        //Actual implementation
        ObjectMapper mapper = new ObjectMapper();

        try {
            String employeeJSON = mapper.writeValueAsString(employee);

            File employeeFile = new File("./src/main/java/com/revature/repository/employee.json");
            employeeFile.createNewFile();

            FileWriter writer = new FileWriter("./src/main/java/com/revature/repository/employee.json");
            writer.write(employeeJSON);
            writer.close();

        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
