package com.example.appjwtmailaudittask.payload;

import com.example.appjwtmailaudittask.entity.Task;
import com.example.appjwtmailaudittask.entity.Turni_Ket;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class EmployeeArrivalTimeAndTasks {

    private String firstName;

    private String lastName;

    List<Turni_Ket> list;

    Set<Task> tasks;
}
