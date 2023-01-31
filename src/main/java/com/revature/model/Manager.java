package com.revature.model;

public class Manager extends User{
    public Manager(String username){
        super(username);
    }

    public void viewPendingRequest(){
        // Managers can view pending requests and select yes or no
    }


    // These are helper methods for interacting with requests
    public void approveRequest(){

    }

    public void denyRequest(){

    }
}
