package com.example.appjwtmailaudittask.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum RoleName {
  DIRECTOR(Set.of(Permission.MANAGER_CRUD, Permission.EMPLOYEE_CRUD, Permission.TASK_FOR_MANAGER, Permission.TASK_FOR_EMPLOYEE)),
  HR_MANAGER(Set.of(Permission.EMPLOYEE_CRUD, Permission.TASK_FOR_EMPLOYEE)),
  MANAGER(Set.of(Permission.TASK_FOR_EMPLOYEE)),
  EMPLOYEE(Set.of());

  public Set<Permission> getPermissions() {
    return permissions;
  }

  private final Set<Permission> permissions;

  RoleName(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  public Set<SimpleGrantedAuthority> getAuthorities(){
    return permissions.stream().map(permission -> new SimpleGrantedAuthority(
      permission.getPermission())).collect(Collectors.toSet());
  }
}
