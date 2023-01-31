package com.revature.model;

public abstract class User {
    private String username;

    public String getUsername(){
        return this.username;
    }

    public User(String username){
        this.username = username;
        System.out.println("User Constructor Call");
    }
    
}
