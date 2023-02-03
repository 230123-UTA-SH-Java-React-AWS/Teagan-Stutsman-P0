package com.revature.model;

public class Manager extends User{
    public Manager(String username){
        super(username);
        this.setManagerStatus(ManagerStatus.MANAGER);
    }

    public Manager(){
        super();
        this.setManagerStatus(ManagerStatus.MANAGER);
    }

    //Only manager objects can call approve/deny?
}
