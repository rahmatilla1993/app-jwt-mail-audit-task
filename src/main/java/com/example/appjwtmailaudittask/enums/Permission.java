package com.example.appjwtmailaudittask.enums;

public enum Permission {

  MANAGER_CRUD("manager:crud"),
  EMPLOYEE_CRUD("employee:crud"),
  TASK_FOR_MANAGER("manager:task"),
  TASK_FOR_EMPLOYEE("employee:task");

  private final String permission;

  public String getPermission() {
    return permission;
  }

  Permission(String permission) {
    this.permission = permission;
  }
}
