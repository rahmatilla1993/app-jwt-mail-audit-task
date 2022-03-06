package com.example.appjwtmailaudittask.repository;

import com.example.appjwtmailaudittask.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {

    boolean existsByEmail(String email);

    boolean existsByIdIsNotAndEmail(UUID id, String email);

    boolean existsByUsername(String username);

    boolean existsByIdIsNotAndUsername(UUID id, String username);

    Optional<Manager> findByEmailAndEmailCode(String email, String emailCode);
}
