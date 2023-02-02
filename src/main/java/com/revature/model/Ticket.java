package com.revature.model;

public class Ticket {
    public enum Status {PENDING, APPROVED, DENIED}

    private int employeeID;
    private float amount;
    private String description;
    private Status status;

    public Ticket(int employeeID, float amount, String description, Status status){
        this.employeeID = employeeID;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
}
