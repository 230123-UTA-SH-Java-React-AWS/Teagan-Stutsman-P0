package com.revature.repository;
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
            mapper.writeValueAsString(employee);
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
