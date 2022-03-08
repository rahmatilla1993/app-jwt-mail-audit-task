package com.example.appjwtmailaudittask.service;

import com.example.appjwtmailaudittask.entity.Employee;
import com.example.appjwtmailaudittask.entity.Manager;
import com.example.appjwtmailaudittask.entity.Task;
import com.example.appjwtmailaudittask.entity.Turni_Ket;
import com.example.appjwtmailaudittask.payload.EmployeeArrivalTimeAndTasks;
import com.example.appjwtmailaudittask.repository.EmployeeRepository;
import com.example.appjwtmailaudittask.repository.ManagerRepository;
import com.example.appjwtmailaudittask.repository.TaskRepository;
import com.example.appjwtmailaudittask.repository.Turni_KetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ManagementService {

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    Turni_KetRepository turni_ketRepository;

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public EmployeeArrivalTimeAndTasks getEmployeeTaskInfo(UUID id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            EmployeeArrivalTimeAndTasks obj = new EmployeeArrivalTimeAndTasks();
            List<Turni_Ket> arrivalTimes = turni_ketRepository.findAllByEmployee_Id(id);
            Set<Task> tasks = taskRepository.getAllTasksByEmployeeId(id);
            obj.setTasks(tasks);
            obj.setList(arrivalTimes);
            obj.setFirstName(employee.getFirstName());
            obj.setLastName(employee.getLastName());
            return obj;
        }
        return null;
    }
}
