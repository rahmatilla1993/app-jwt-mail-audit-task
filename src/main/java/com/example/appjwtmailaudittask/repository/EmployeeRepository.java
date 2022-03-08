package com.example.appjwtmailaudittask.repository;

import com.example.appjwtmailaudittask.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<Employee> findByEmailAndEmailCode(String email, String emailCode);
}
