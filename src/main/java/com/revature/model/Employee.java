package com.revature.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

// import com.revature.service.EmployeeService;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee extends User{
    public Employee(String username) {
        super(username);
    }

    public Employee(){
        super("user_default");
        //System.out.println("Default employee object created");
    }

    // public void addReimbursementRequest(float amt, String description, int status){
    //     // Employee can request a reimbursement
    //     EmployeeService.requestReimbursement(amt, description, status);
    // }

    // public void viewUpdatedStatus(){
    //     // Employee can view the status of their requests/ request history
    // }
    
}
