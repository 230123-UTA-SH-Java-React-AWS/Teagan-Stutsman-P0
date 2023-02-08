package com.revature.model;

public class Ticket {
    public enum Status {
        PENDING(0), APPROVED(1), DENIED(2);

        private final int value;
        private Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ReimbursementType {
        OTHER(0), TRAVEL(1), LODGING(2), FOOD(3);

        private final int value;
        private ReimbursementType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private int ticketID;
    private String username;
    private int employeeID;
    private float amount;
    private String description;
    private Status status;
    private ReimbursementType reimbursementType;

    // No username option
    public Ticket(int ticketID, int employeeID, float amount, String description, Status status, ReimbursementType reimbursementType){
        this.ticketID = ticketID;
        this.employeeID = employeeID;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.reimbursementType = reimbursementType;
    }

    // Empty Option
    public Ticket(){
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
    public int getTicketID() {
        return ticketID;
    }
    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public ReimbursementType getReimbursementType() {
        return reimbursementType;
    }

    public void setReimbursementType(ReimbursementType reimbursementType) {
        this.reimbursementType = reimbursementType;
    }
}
