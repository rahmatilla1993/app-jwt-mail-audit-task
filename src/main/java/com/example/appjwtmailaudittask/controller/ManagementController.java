package com.example.appjwtmailaudittask.controller;

import com.example.appjwtmailaudittask.entity.Employee;
import com.example.appjwtmailaudittask.entity.Manager;
import com.example.appjwtmailaudittask.payload.EmployeeArrivalTimeAndTasks;
import com.example.appjwtmailaudittask.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/management")
public class ManagementController {

    @Autowired
    ManagementService managementService;

    @GetMapping("/managers")
    public HttpEntity<?> getAllManagers() {
        List<Manager> allManagers = managementService.getAllManagers();
        return ResponseEntity.ok(allManagers);
    }

    @GetMapping("/employees")
    public HttpEntity<?> getAllEmployees() {
        List<Employee> allEmployees = managementService.getAllEmployees();
        return ResponseEntity.ok(allEmployees);
    }

    @GetMapping("/employeeTaskInfo/{emp_id}")
    public HttpEntity<?> getEmployeeInfo(@PathVariable UUID emp_id) {
        EmployeeArrivalTimeAndTasks employeeArrivalTimeAndTasks = managementService.getEmployeeTaskInfo(emp_id);
        return ResponseEntity.status(employeeArrivalTimeAndTasks == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(employeeArrivalTimeAndTasks);
    }
}
