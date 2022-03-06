package com.example.appjwtmailaudittask.entity;

import com.example.appjwtmailaudittask.enums.RoleName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Collection;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Director extends User {

  @OneToOne
  private Company company;

  @OneToMany
  private Set<Task> tasks;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return RoleName.DIRECTOR.getAuthorities();
  }
}
