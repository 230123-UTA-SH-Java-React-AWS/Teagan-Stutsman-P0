package com.revature.model;

public class Ticket {
    private float amount;
    private String description;
    private int status;

    public Ticket(float amount, String description, int status){
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
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
