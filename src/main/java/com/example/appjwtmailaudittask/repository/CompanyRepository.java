package com.example.appjwtmailaudittask.repository;

import com.example.appjwtmailaudittask.custom.CustomCompany;
import com.example.appjwtmailaudittask.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "company", excerptProjection = CustomCompany.class)
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
