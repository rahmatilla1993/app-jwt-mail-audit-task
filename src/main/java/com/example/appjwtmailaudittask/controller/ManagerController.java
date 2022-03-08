package com.example.appjwtmailaudittask.controller;

import com.example.appjwtmailaudittask.entity.Manager;
import com.example.appjwtmailaudittask.payload.ApiResponse;
import com.example.appjwtmailaudittask.payload.LoginDto;
import com.example.appjwtmailaudittask.payload.ManagerDto;
import com.example.appjwtmailaudittask.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @GetMapping("/crud")
    public HttpEntity<?> getAllManagers() {
        List<Manager> managers = managerService.getAllManagers();
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/crud/{id}")
    public HttpEntity<?> getManagerById(@PathVariable UUID id) {
        ApiResponse response = managerService.getManagerById(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/crud")
    public HttpEntity<?> addManager(@RequestBody ManagerDto managerDto) {
        ApiResponse response = managerService.addManager(managerDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(response);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email) {
        ApiResponse apiResponse = managerService.verifyEmail(email, emailCode);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> loginToSystem(@RequestBody LoginDto loginDto) {
        ApiResponse response = managerService.loginToSystem(loginDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }

    @PutMapping("/crud/{id}")
    public HttpEntity<?> editManagerById(@PathVariable UUID id, @RequestBody ManagerDto managerDto) {
        ApiResponse response = managerService.editManagerById(id, managerDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_ACCEPTABLE).body(response);
    }

    @DeleteMapping("/crud/{id}")
    public HttpEntity<?> deleteManagerById(@PathVariable UUID id) {
        ApiResponse response = managerService.deleteManagerById(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/task/{emp_id}")
    public HttpEntity<?> taskForEmployee(@PathVariable UUID emp_id, @RequestParam Integer task_id) {
        ApiResponse apiResponse = managerService.taskForEmployee(emp_id, task_id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }
}
