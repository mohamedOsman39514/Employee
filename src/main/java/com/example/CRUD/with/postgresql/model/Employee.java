package com.example.CRUD.with.postgresql.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    @Column(name = "employee_name")
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String name;

    @Id
    @Column(name = "employee_email")
    private String email;

    @Column(name = "employee_password")
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private List<Role> roles = new ArrayList<>();

}
