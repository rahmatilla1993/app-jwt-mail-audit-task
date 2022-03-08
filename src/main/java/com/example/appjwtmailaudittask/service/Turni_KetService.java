package com.example.appjwtmailaudittask.service;

import com.example.appjwtmailaudittask.entity.Employee;
import com.example.appjwtmailaudittask.entity.Turni_Ket;
import com.example.appjwtmailaudittask.payload.ApiResponse;
import com.example.appjwtmailaudittask.payload.TurniKetDto;
import com.example.appjwtmailaudittask.repository.EmployeeRepository;
import com.example.appjwtmailaudittask.repository.Turni_KetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Turni_KetService {

    @Autowired
    Turni_KetRepository turni_ketRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Turni_Ket> getAllInfo() {
        return turni_ketRepository.findAll();
    }

    public ApiResponse addInfo(TurniKetDto turniKetDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(turniKetDto.getEmployee_id());
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            Turni_Ket turni_ket = new Turni_Ket();
            turni_ket.setInput_time(turniKetDto.getInput_time());
            turni_ket.setOutput_time(turniKetDto.getOutput_time());
            turni_ket.setEmployee(employee);
            return new ApiResponse("Info added", true);
        }
        return new ApiResponse("Employee not found", false);
    }
}
