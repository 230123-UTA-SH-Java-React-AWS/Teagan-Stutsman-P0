package com.revature.model;

public abstract class User {
    public enum ManagerStatus {EMPLOYEE, MANAGER};
    
    private String username;
    private ManagerStatus managerStatus;
    private int employeeID;

    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    public String getUsername(){
        return this.username;
    }
    public ManagerStatus getManagerStatus(){
        return this.managerStatus;
    }
    public void setManagerStatus(ManagerStatus newManagerStatus){
        this.managerStatus = newManagerStatus;
    }

    public User(String username){
        this.username = username;
        this.managerStatus = ManagerStatus.EMPLOYEE;
        //System.out.println("User Created");
    }

    public User(){
        this.username = "user_default";
        this.managerStatus = ManagerStatus.EMPLOYEE;
        //System.out.println("Default User Created");
    }
}
