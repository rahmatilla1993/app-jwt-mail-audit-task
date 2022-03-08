package com.example.appjwtmailaudittask.repository;

import com.example.appjwtmailaudittask.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByEmail(String email);

    boolean existsByIdIsNotAndEmail(UUID id, String email);

    boolean existsByUsername(String username);

    boolean existsByIdIsNotAndUsername(UUID id, String username);

    Optional<Employee> findByEmailAndEmailCode(String email, String emailCode);

    @Query(value = "SELECT employee.* FROM employee INNER JOIN employee_tasks et ON employee.id = et.employee_id\n" +
            "    INNER JOIN task t ON t.id = et.tasks_id WHERE t.id = ?1", nativeQuery = true)
    Employee findByTaskId(Integer task_id);

}
