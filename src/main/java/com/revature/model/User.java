package com.revature.model;
import com.revature.service.EmployeeService;

public abstract class User {
    private String username;
    private String password;

    public void login(String un, String pw){
        // Service method call
        int success = EmployeeService.login(un, pw);
        System.out.println(success);
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public User(String un, String pw){
        this.username = un;
        this.password = pw;
    }
    
}
