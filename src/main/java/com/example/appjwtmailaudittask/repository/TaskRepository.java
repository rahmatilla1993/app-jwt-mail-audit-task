package com.example.appjwtmailaudittask.repository;

import com.example.appjwtmailaudittask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    boolean existsByName(String name);

    boolean existsByIdIsNotAndName(Integer id, String name);

    @Query(value = "SELECT t.* FROM employee INNER JOIN employee_tasks et ON employee.id = et.employee_id\n" +
            "    INNER JOIN task t on t.id = et.tasks_id WHERE employee.id = ?1", nativeQuery = true)
    Set<Task> getAllTasksByEmployeeId(UUID id);
}
