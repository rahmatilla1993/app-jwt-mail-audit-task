package com.example.appjwtmailaudittask.controller;

import com.example.appjwtmailaudittask.entity.Company;
import com.example.appjwtmailaudittask.entity.Director;
import com.example.appjwtmailaudittask.payload.DirectorDto;
import com.example.appjwtmailaudittask.repository.CompanyRepository;
import com.example.appjwtmailaudittask.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/director")
public class DirectorController {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DirectorRepository directorRepository;

    @PostMapping
    public HttpEntity<?> addDirector(@RequestBody DirectorDto directorDto) {
        Director director = new Director();
        Optional<Company> optionalCompany = companyRepository.findById(directorDto.getCompany_id());
        if (optionalCompany.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Company company = optionalCompany.get();
        director.setCompany(company);
        director.setEmail(directorDto.getEmail());
        director.setPassword(directorDto.getPassword());
        director.setFirstName(directorDto.getFirstName());
        director.setLastName(directorDto.getLastName());
        director.setUsername(directorDto.getUsername());
        director.setEnabled(true);
        directorRepository.save(director);
        return ResponseEntity.ok("Director saved");
    }
}
