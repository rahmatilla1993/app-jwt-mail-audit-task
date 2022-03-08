package com.example.appjwtmailaudittask.service;

import com.example.appjwtmailaudittask.entity.Employee;
import com.example.appjwtmailaudittask.entity.Manager;
import com.example.appjwtmailaudittask.entity.Task;
import com.example.appjwtmailaudittask.payload.*;
import com.example.appjwtmailaudittask.repository.EmployeeRepository;
import com.example.appjwtmailaudittask.repository.ManagerRepository;
import com.example.appjwtmailaudittask.repository.TaskRepository;
import com.example.appjwtmailaudittask.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    TaskRepository taskRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public ApiResponse getEmployeeById(UUID id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            return new ApiResponse("Employee not found", false);
        }
        return new ApiResponse(true, optionalEmployee.get());
    }

    public ApiResponse addEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
            return new ApiResponse("This email already exits", false);
        }
        if (employeeRepository.existsByUsername(employeeDto.getUsername())) {
            return new ApiResponse("This username already exits", false);
        }
        Employee employee = new Employee();
        employee.setEmail(employeeDto.getEmail());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setUsername(employeeDto.getUsername());
        employee.setEmailCode(UUID.randomUUID().toString());
        employeeRepository.save(employee);
        Manager manager = managerRepository.getById(employee.getCreatedBy());
        String link = sendEmail(manager.getEmail(), employee.getEmail(), employee.getEmailCode());
        return new ApiResponse("Set a password for the account using the link provided", link);
    }

    private String sendEmail(String fromEmail, String toEmail, String emailCode) {
        String link = "http://localhost:8080/api/employee/verifyEmail?emailCode=" + emailCode + "&email=" + toEmail;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Message title");
        mailMessage.setText(link);
        javaMailSender.send(mailMessage);
        return link;
    }

    public ApiResponse addPasswordByLink(String emailCode, String email, String password) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setEmail(password);
            employee.setEmailCode(null);
            employee.setEnabled(true);
            employeeRepository.save(employee);
            return new ApiResponse("Account verified successfully", true);
        }
        return new ApiResponse("Account already confirmed", false);
    }

    public ApiResponse loginToSystem(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()));

            Employee employee = (Employee) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(employee.getUsername());
            return new ApiResponse("Token generated", true, token);
        } catch (Exception e) {
            return new ApiResponse("Employee not found", false);
        }
    }

    public ApiResponse deleteEmployeeById(UUID id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            employeeRepository.delete(optionalEmployee.get());
            return new ApiResponse("Employee deleted", true);
        }
        return new ApiResponse("Employee not found", false);
    }

    public ApiResponse getTaskById(Integer task_id) {
        Optional<Task> optionalTask = taskRepository.findById(task_id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus("Task completed");
            taskRepository.save(task);
            Manager manager = managerRepository.getById(task.getCreatedAt());
            Employee employee = employeeRepository.findByTaskId(task_id);
            String response = sendResponseToTask(employee.getEmail(), manager.getEmail());
            return new ApiResponse(true, response);
        }
        return new ApiResponse("Task not found", false);
    }

    private String sendResponseToTask(String fromEmail, String toEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Answer");
        mailMessage.setText("Task completed");
        javaMailSender.send(mailMessage);
        return "Task completed";
    }

    public ApiResponse editEmployeeById(UUID id, EmployeeEditDto employeeEditDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            if (employeeRepository.existsByIdIsNotAndEmail(id, employeeEditDto.getEmail())) {
                return new ApiResponse("This email already exists", false);
            }
            if (employeeRepository.existsByIdIsNotAndUsername(id, employeeEditDto.getUsername())) {
                return new ApiResponse("This user already exists", false);
            }
            employee.setEmail(employeeEditDto.getEmail());
            employee.setUsername(employeeEditDto.getUsername());
            employee.setFirstName(employeeEditDto.getFirstName());
            employee.setLastName(employeeEditDto.getLastName());
            employee.setPassword(employeeEditDto.getPassword());
            employeeRepository.save(employee);
            return new ApiResponse("Employee edited", true);
        }
        return new ApiResponse("Employee not found", false);
    }

    public ApiResponse addSalaryForEmployee(UUID id, EmployeeSalary employeeSalary){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            employee.setMonth_number(employeeSalary.getMonth_number());
            employee.setSalary(employeeSalary.getSalary());
            employeeRepository.save(employee);
            return new ApiResponse("Salary added",true);
        }
        return new ApiResponse("Employee not found",false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username)) {
                return employee;
            }
        }
        throw new UsernameNotFoundException("Employee not found");
    }
}
