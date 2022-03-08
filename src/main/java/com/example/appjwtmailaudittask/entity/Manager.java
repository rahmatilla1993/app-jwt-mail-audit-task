package com.example.appjwtmailaudittask.entity;

import com.example.appjwtmailaudittask.enums.RoleName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import java.util.Collection;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Manager extends User {

  @ManyToOne
  private Role role;

  @CreatedBy
  private UUID createdBy;

  @LastModifiedBy
  private UUID updatedBy;

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
