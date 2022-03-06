package com.example.appjwtmailaudittask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String status;

  private String description;

  @ManyToOne
  private Director director;

  @ManyToOne
  private Manager manager;

  @Column(nullable = false)
  private Date start_task;

  @Column(nullable = false)
  private Date end_task;
}
