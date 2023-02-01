package com.revature.repository;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import com.revature.model.Employee;
import com.revature.util.ConnectionUtil;

// Repository layer is responsible for interacting w/ database + sending/receiving info from database
public class EmployeeRepository {
    // Stores employees in postgresql database 'employees'
    public void registerNewEmployee(Employee employee, String password){
        // Format SQL Statement
        String sql = "INSERT INTO employees (username, userpassword) VALUES (?, ?)";

        try (Connection con = ConnectionUtil.getConnection()) {

            PreparedStatement prstmt = con.prepareStatement(sql);

            //Replace '?' with actual value from new employee
            //1 based indexing ugh
            prstmt.setString(1, employee.getUsername());
            prstmt.setString(2, password);

            //execute() method - no return
            //executeQuery() method - expects return
            prstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public HashSet<String> getRegisteredEmployees(){
        // employeeList - Hash set of usernames
        HashSet<String> employeeList = new HashSet<String>();
        String sql = "SELECT username FROM employees";

        try (Connection con = ConnectionUtil.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                employeeList.add(rs.getString(1));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return employeeList;
    }
    
}
