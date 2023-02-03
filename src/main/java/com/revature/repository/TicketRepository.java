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
    public void submitNewTicket(int employeeID, float amount, String description){
        String sql = "INSERT INTO tickets (employeeID, amount, description, status) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectionUtil.getConnection()) {
            
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, employeeID);
            preparedStatement.setFloat(2, amount);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, 0);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Ticket> getAllTickets(){
        ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
        String sql = "SELECT * FROM tickets";

        try (Connection con = ConnectionUtil.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int ticketid = rs.getInt(1);
                Float amount = Float.parseFloat(rs.getString(2));
                String description = rs.getString(3);
                Ticket.Status status = Ticket.Status.values()[rs.getInt(4)];
                int employeeid = rs.getInt(5);

                ticketList.add(new Ticket(ticketid, employeeid, amount, description, status));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return ticketList;
    }
}
