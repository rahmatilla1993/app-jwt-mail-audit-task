package com.example.appjwtmailaudittask.controller;

import com.example.appjwtmailaudittask.entity.Task;
import com.example.appjwtmailaudittask.payload.ApiResponse;
import com.example.appjwtmailaudittask.payload.TaskDto;
import com.example.appjwtmailaudittask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping
    public HttpEntity<?> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getTaskById(@PathVariable Integer id) {
        ApiResponse response = taskService.getTaskById(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping
    public HttpEntity<?> addTask(@RequestBody TaskDto taskDto) {
        ApiResponse apiResponse = taskService.addTask(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTaskById(@PathVariable Integer id) {
        ApiResponse apiResponse = taskService.deleteTaskById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editTaskById(@PathVariable Integer id,@RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.editTaskById(id, taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(apiResponse);
    }
}
