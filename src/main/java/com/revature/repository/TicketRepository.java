package com.revature.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.model.Ticket;
import com.revature.util.ConnectionUtil;

public class TicketRepository {
    public void submitNewTicket(float amount, String description){
        String sql = "INSERT INTO tickets (amount, description, status) VALUES (?, ?, ?)";

        try (Connection con = ConnectionUtil.getConnection()) {
            
            PreparedStatement prstmt = con.prepareStatement(sql);

            prstmt.setFloat(1, amount);
            prstmt.setString(2, description);
            prstmt.setInt(3, 0);

            prstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Ticket> getAllTickets(){
        // TODO: figure out how to spit ticketlist to postman for a specific employee
        ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
        String sql = "SELECT e.employeeid, t.amount, t.description, t.status FROM tickets t INNER JOIN employee_ticket et ON t.ticketid=et.ticketid INNER JOIN employees e ON et.employeeid=e.employeeid";

        try (Connection con = ConnectionUtil.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int employeeid = rs.getInt(1);
                Float amount = Float.parseFloat(rs.getString(2));
                String description = rs.getString(3);
                Ticket.Status status = Ticket.Status.values()[rs.getInt(4)];

                ticketList.add(new Ticket(employeeid, amount, description, status));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return ticketList;
    }
}
