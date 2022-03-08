package com.example.appjwtmailaudittask.controller;

import com.example.appjwtmailaudittask.entity.Employee;
import com.example.appjwtmailaudittask.payload.ApiResponse;
import com.example.appjwtmailaudittask.payload.EmployeeDto;
import com.example.appjwtmailaudittask.payload.LoginDto;
import com.example.appjwtmailaudittask.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public HttpEntity<?> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getEmployeeById(@PathVariable UUID id) {
        ApiResponse response = employeeService.getEmployeeById(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping
    public HttpEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto) {
        ApiResponse apiResponse = employeeService.addEmployee(employeeDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> addPasswordByLink(@RequestParam String emailCode,
                                           @RequestParam String email, @RequestBody LoginDto password) {
        ApiResponse apiResponse = employeeService.addPasswordByLink(emailCode, email, passwordEncoder.encode(password.getPassword()));
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.NOT_ACCEPTABLE).body(apiResponse);
    }

    @PostMapping
    public HttpEntity<?> loginToSystem(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = employeeService.loginToSystem(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteEmployeeById(@PathVariable UUID id) {
        ApiResponse apiResponse = employeeService.deleteEmployeeById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(apiResponse);
    }
}
