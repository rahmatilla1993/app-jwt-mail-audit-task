package com.example.appjwtmailaudittask.entity;

import com.example.appjwtmailaudittask.enums.RoleName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Employee extends User {

  @ManyToOne
  private Company company;

  @OneToMany
  private Set<Task> tasks;

  @Column(nullable = false)
  private Double salary;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return RoleName.EMPLOYEE.getAuthorities();
  }
}
