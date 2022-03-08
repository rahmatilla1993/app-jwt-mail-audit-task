package com.example.appjwtmailaudittask.service;

import com.example.appjwtmailaudittask.entity.Manager;
import com.example.appjwtmailaudittask.entity.Role;
import com.example.appjwtmailaudittask.payload.ApiResponse;
import com.example.appjwtmailaudittask.payload.LoginDto;
import com.example.appjwtmailaudittask.payload.ManagerDto;
import com.example.appjwtmailaudittask.repository.ManagerRepository;
import com.example.appjwtmailaudittask.repository.RoleRepository;
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
public class ManagerService implements UserDetailsService {

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public ApiResponse getManagerById(UUID id) {
        Optional<Manager> optionalManager = managerRepository.findById(id);
        if (optionalManager.isEmpty()) {
            return new ApiResponse("Manager not found!", false);
        }
        return new ApiResponse(true, optionalManager.get());
    }

    public ApiResponse addManager(ManagerDto managerDto) {
        if (managerRepository.existsByEmail(managerDto.getEmail())) {
            return new ApiResponse("This email already exists", false);
        }
        if (managerRepository.existsByUsername(managerDto.getUsername())) {
            return new ApiResponse("This username already exists", false);
        }
        Optional<Role> optionalRole = roleRepository.findById(managerDto.getRole_id());
        if (optionalRole.isEmpty()) {
            return new ApiResponse("Role not found", false);
        }
        Role role = optionalRole.get();
        Manager manager = new Manager();
        manager.setRole(role);
        manager.setEmail(managerDto.getEmail());
        manager.setFirstName(managerDto.getFirstName());
        manager.setLastName(managerDto.getLastName());
        manager.setPassword(passwordEncoder.encode(managerDto.getPassword()));
        manager.setEmailCode(UUID.randomUUID().toString());
        manager.setUsername(managerDto.getUsername());
        managerRepository.save(manager);
        Boolean sendMessage = sendMessage(manager.getEmail(), manager.getEmailCode());
        return new ApiResponse("Go to email to confirm the account", sendMessage);
    }

    public Boolean sendMessage(String toEmail, String emailCode) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("example@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Message title");
        mailMessage.setText("http://localhost:8080/api/manager/register/verifyEmail?email=" + toEmail + "&emailCode=" + emailCode);
        javaMailSender.send(mailMessage);
        return true;
    }

    public ApiResponse verifyEmail(String email, String emailCode) {
        Optional<Manager> optionalManager = managerRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalManager.isEmpty()) {
            return new ApiResponse("Account already exists", false);
        }
        Manager manager = optionalManager.get();
        manager.setEmailCode(null);
        manager.setEnabled(true);
        managerRepository.save(manager);
        return new ApiResponse("Account confirmed successfully", true);
    }

    public ApiResponse loginToSystem(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()));
            Manager manager = (Manager) authenticate.getCredentials();
            String token = jwtProvider.generateToken(manager.getUsername());
            return new ApiResponse("Token generated", true, token);
        } catch (Exception e) {
            return new ApiResponse("Manager not found", false);
        }
    }

    public ApiResponse editManagerById(UUID id, ManagerDto managerDto) {
        Optional<Manager> optionalManager = managerRepository.findById(id);
        if (optionalManager.isEmpty()) {
            return new ApiResponse("Manager not found", false);
        }
        Manager manager = optionalManager.get();
        if (managerRepository.existsByIdIsNotAndEmail(id, managerDto.getEmail())) {
            return new ApiResponse("Email already exists", false);
        }
        if (managerRepository.existsByIdIsNotAndUsername(id, managerDto.getUsername())) {
            return new ApiResponse("Username already exists", false);
        }
        Optional<Role> optionalRole = roleRepository.findById(managerDto.getRole_id());
        if (optionalRole.isEmpty()) {
            return new ApiResponse("Role not found", false);
        }
        Role role = optionalRole.get();
        manager.setEmail(managerDto.getEmail());
        manager.setPassword(managerDto.getPassword());
        manager.setFirstName(managerDto.getFirstName());
        manager.setLastName(managerDto.getLastName());
        manager.setRole(role);
        manager.setUsername(managerDto.getUsername());
        managerRepository.save(manager);
        return new ApiResponse("Manager edited", true);
    }

    public ApiResponse deleteManagerById(UUID id) {
        Optional<Manager> optionalManager = managerRepository.findById(id);
        if (optionalManager.isPresent()) {
            managerRepository.delete(optionalManager.get());
            return new ApiResponse("Manager deleted", true);
        }
        return new ApiResponse("Manager not found", false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Manager> managers = managerRepository.findAll();
        for (Manager manager : managers) {
            if (manager.getUsername().equals(username)) {
                return manager;
            }
        }
        throw new UsernameNotFoundException("Manager not found");
    }
}
