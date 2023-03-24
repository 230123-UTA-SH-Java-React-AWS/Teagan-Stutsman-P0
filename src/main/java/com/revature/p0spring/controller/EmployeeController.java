package com.revature.p0spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.p0spring.model.LoginDto;
import com.revature.p0spring.model.PromoteDto;
import com.revature.p0spring.service.EmployeeService;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private EmployeeService employeeService;
    
    @Autowired
    public EmployeeController( EmployeeService employeeService ){
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        return employeeService.login(loginDto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginDto registerDto){
        return employeeService.register(registerDto);
    }

    @PutMapping("/changeManagerStatus")
    public ResponseEntity<String> changeEmployee(@RequestBody PromoteDto promoteDto){
        return employeeService.changeEmployee(promoteDto);
    }
}
