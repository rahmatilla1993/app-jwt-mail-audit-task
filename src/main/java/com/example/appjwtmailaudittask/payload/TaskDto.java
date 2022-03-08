package com.example.appjwtmailaudittask.payload;

import lombok.Data;
import java.util.Date;

@Data
public class TaskDto {

    private String name;

    private String status;

    private String description;

    private Date start_task;

    private Date end_task;
}
