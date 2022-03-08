package com.example.appjwtmailaudittask.controller;

import com.example.appjwtmailaudittask.entity.Employee;
import com.example.appjwtmailaudittask.payload.*;
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

    @GetMapping("/crud")
    public HttpEntity<?> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/crud/{id}")
    public HttpEntity<?> getEmployeeById(@PathVariable UUID id) {
        ApiResponse response = employeeService.getEmployeeById(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/crud")
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

    @PostMapping("/login")
    public HttpEntity<?> loginToSystem(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = employeeService.loginToSystem(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping("/crud{id}")
    public HttpEntity<?> deleteEmployeeById(@PathVariable UUID id) {
        ApiResponse apiResponse = employeeService.deleteEmployeeById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping("/task/{task_id}")
    public HttpEntity<?> getTaskById(@PathVariable Integer task_id) {
        ApiResponse response = employeeService.getTaskById(task_id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }

    @PutMapping("/crud/{id}")
    public HttpEntity<?> editEmployeeById(@PathVariable UUID id, @RequestBody EmployeeEditDto employeeEditDto) {
        ApiResponse apiResponse = employeeService.editEmployeeById(id, employeeEditDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_ACCEPTABLE).body(apiResponse);
    }

    @PostMapping("/addSalary/{id}")
    public HttpEntity<?> addSalaryForEmployee(@PathVariable UUID id, @RequestBody EmployeeSalary employeeSalary) {
        ApiResponse apiResponse = employeeService.addSalaryForEmployee(id, employeeSalary);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND).body(apiResponse);
    }
}
