package com.example.appjwtmailaudittask.repository;

import com.example.appjwtmailaudittask.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DirectorRepository extends JpaRepository<Director, UUID> {
}
