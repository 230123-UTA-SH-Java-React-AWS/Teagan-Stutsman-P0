package com.revature.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee extends User{
    public Employee(String username) {
        super(username);
    }

    public Employee(){
        super();
    }    
}
