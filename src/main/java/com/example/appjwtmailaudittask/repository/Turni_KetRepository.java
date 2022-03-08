package com.example.appjwtmailaudittask.repository;

import com.example.appjwtmailaudittask.entity.Turni_Ket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface Turni_KetRepository extends JpaRepository<Turni_Ket,Integer> {

    List<Turni_Ket> findAllByEmployee_Id(UUID employee_id);
}
