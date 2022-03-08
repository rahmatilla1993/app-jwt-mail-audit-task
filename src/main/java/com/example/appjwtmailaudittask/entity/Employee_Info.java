package com.example.appjwtmailaudittask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee_Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Employee employee;

    @Column(nullable = false)
    private Double salary;

    private Integer month_number;

    @ManyToOne
    private Task task;
}
