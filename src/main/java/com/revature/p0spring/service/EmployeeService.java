package com.revature.p0spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.revature.p0spring.model.Employee;
import com.revature.p0spring.model.LoginDto;
import com.revature.p0spring.model.PromoteDto;
import com.revature.p0spring.model.Employee.ManagerStatus;
import com.revature.p0spring.repository.EmployeeRepository;

@Service
public class EmployeeService {
    
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService( EmployeeRepository employeeRepository ){
        this.employeeRepository = employeeRepository;
    }

    public ResponseEntity<String> login(LoginDto loginDto){
        if(Boolean.TRUE.equals(employeeRepository.existsByEmail(loginDto.getEmail()))){
            Employee employee = employeeRepository.findByEmail(loginDto.getEmail()).get();
            if (loginDto.getPassword().equals(employee.getPassword())){
                return ResponseEntity.status(200).body("You're Logged In! Welcome");
            } else{
                return ResponseEntity.status(403).body("Invalid Password");
            }
        } else{
            return ResponseEntity.status(404).body("Error - Email Not Recognized");
        }
    }

    public ResponseEntity<String> register(LoginDto registerDto){
        // check if email already exists
        if(Boolean.TRUE.equals(employeeRepository.existsByEmail(registerDto.getEmail()))){
            return ResponseEntity.status(403).body("Email Already Taken");
        }
        // create employee and set credentials
        Employee employee = new Employee();
        employee.setEmail(registerDto.getEmail());
        employee.setPassword(registerDto.getPassword());
        employee.setManagerStatus(ManagerStatus.EMPLOYEE);

        // save the employee to the database
        employeeRepository.save(employee);

        return ResponseEntity.status(200).body("You're Registered! Welcome");
    }

    public ResponseEntity<String> changeEmployee(PromoteDto promoteDto){

        Optional<Employee> manager = employeeRepository.findByEmail(promoteDto.getManagerEmail());

        if(manager.isPresent() && manager.get().getManagerStatus().equals(ManagerStatus.MANAGER)){
            Optional<Employee> newManager = employeeRepository.findByEmail(promoteDto.getEmployeeEmail());
            if(newManager.isPresent()){
                newManager.get().setManagerStatus(ManagerStatus.MANAGER);
                employeeRepository.save(newManager.get());
                return ResponseEntity.status(200).body("Employee manager status successfully changed");
            }
            return ResponseEntity.status(400).body("Employee email not recognized");
        }

        return ResponseEntity.status(400).body("You are not a manager");
    }
}
