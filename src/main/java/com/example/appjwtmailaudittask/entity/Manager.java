package com.example.appjwtmailaudittask.entity;

import com.example.appjwtmailaudittask.enums.RoleName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Manager extends User {

  @ManyToOne
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (role.getRoleName().name().equals(RoleName.MANAGER.name())) {
      return RoleName.MANAGER.getAuthorities();
    } else if (role.getRoleName().name().equals(RoleName.HR_MANAGER.name())) {
      return RoleName.HR_MANAGER.getAuthorities();
    }
    return null;
  }
}
