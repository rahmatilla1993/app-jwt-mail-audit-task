package com.example.appjwtmailaudittask.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEditDto {

    @Size(min = 5,max = 50)
    private String firstName;

    @Length(min = 5,max = 50)
    private String lastName;

    @Email
    private String email;

    @NotNull
    private String username;

    private String password;
}
