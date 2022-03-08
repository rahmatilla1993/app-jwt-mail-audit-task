package com.example.appjwtmailaudittask.service;

import com.example.appjwtmailaudittask.entity.Task;
import com.example.appjwtmailaudittask.payload.ApiResponse;
import com.example.appjwtmailaudittask.payload.TaskDto;
import com.example.appjwtmailaudittask.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public ApiResponse getTaskById(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            return new ApiResponse(true, task);
        }
        return new ApiResponse("Task not found", false);
    }

    public ApiResponse addTask(TaskDto taskDto) {
        Task task = new Task();
        if (taskRepository.existsByName(taskDto.getName())) {
            return new ApiResponse("Task already exists", false);
        }
        task.setStatus(taskDto.getStatus());
        task.setName(taskDto.getName());
        task.setStart_task(taskDto.getStart_task());
        task.setEnd_task(taskDto.getEnd_task());
        task.setDescription(taskDto.getDescription());
        taskRepository.save(task);
        return new ApiResponse("Task added", true, task);
    }

    public ApiResponse deleteTaskById(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            taskRepository.delete(optionalTask.get());
            return new ApiResponse("Task deleted", true);
        }
        return new ApiResponse("Task not found", false);
    }

    public ApiResponse editTaskById(Integer id, TaskDto taskDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (taskRepository.existsByIdIsNotAndName(id, taskDto.getName())) {
                return new ApiResponse("This task already exists", false);
            }
            task.setStatus(taskDto.getStatus());
            task.setName(taskDto.getName());
            task.setStart_task(taskDto.getStart_task());
            task.setEnd_task(taskDto.getEnd_task());
            task.setDescription(taskDto.getDescription());
            taskRepository.save(task);
            return new ApiResponse("Task edited", true, task);
        }
        return new ApiResponse("Task not found", false);
    }
}
