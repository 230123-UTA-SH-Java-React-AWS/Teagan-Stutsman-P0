package com.revature.repository;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import com.revature.model.Employee;
import com.revature.model.User;
import com.revature.model.User.ManagerStatus;
import com.revature.util.ConnectionUtil;

// Repository layer is responsible for interacting w/ database + sending/receiving info from database
public class EmployeeRepository {
    // Stores employees in postgresql database 'employees'
    public void registerNewEmployee(Employee employee, String password){
        // Format SQL Statement
        String sql = "INSERT INTO employees (username, userpassword, managerstatus) VALUES (?, ?, ?)";

        try (Connection con = ConnectionUtil.getConnection()) {

            PreparedStatement prstmt = con.prepareStatement(sql);

            //Replace '?' with actual value from new employee
            //1 based indexing ugh
            prstmt.setString(1, employee.getUsername());
            prstmt.setString(2, password);
            prstmt.setInt(3, employee.getManagerStatus().getValue());

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

    public HashMap<Integer, String> getEmployeesByID(){
        HashMap<Integer, String> employeeIDs = new HashMap<Integer, String>();
        String sql = "SELECT employeeid, username FROM employees";

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                employeeIDs.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employeeIDs;
    }

    public HashMap<String, Integer> getIDsByEmployee(){
        HashMap<String, Integer> idEmployees = new HashMap<String, Integer>();
        String sql = "SELECT username, employeeid FROM employees";

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                idEmployees.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return idEmployees;
    }

    public User getEmployee(String username){
        User user = new Employee();
        String sql = "SELECT employeeid, managerstatus FROM employees WHERE username LIKE ?"; //0(1) if serialized fields are like hash tables

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + username + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            user.setEmployeeID(resultSet.getInt(1));
            user.setManagerStatus(ManagerStatus.values()[resultSet.getInt(2)]);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public HashMap<String, String> getEmployeePasswords(){
        HashMap<String, String> employeePasswords = new HashMap<String, String>();
        String sql = "SELECT username, userpassword FROM employees";

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                employeePasswords.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employeePasswords;
    }

    public void changeEmployeeManagerStatus(String username, ManagerStatus managerStatus){
        String sql = "UPDATE employees SET managerstatus = ? WHERE username = ?";

        try(Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, managerStatus.getValue());
            preparedStatement.setString(2, username);
            preparedStatement.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // public void changeEmployeePassword(String username, String newPassword){
    //     String sql = "UPDATE employees SET userpassword=? WHERE username=?";

    //     try (Connection connection = ConnectionUtil.getConnection()) {

    //         PreparedStatement preparedStatement = connection.prepareStatement(sql);

    //         preparedStatement.setString(1, newPassword);
    //         preparedStatement.setString(2, username);
    //         preparedStatement.execute();

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
