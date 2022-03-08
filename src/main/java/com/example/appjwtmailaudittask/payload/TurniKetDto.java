package com.example.appjwtmailaudittask.payload;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class TurniKetDto {

    private Timestamp input_time;

    private Timestamp output_time;

    private UUID employee_id;
}
