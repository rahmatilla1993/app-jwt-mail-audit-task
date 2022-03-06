package com.example.appjwtmailaudittask.payload;

import lombok.Data;

@Data
public class DirectorDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String username;

    private Integer company_id;
}
