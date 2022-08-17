package com.example.CRUD.with.postgresql.mapStruct.dtos;

import com.example.CRUD.with.postgresql.model.Department;
import com.example.CRUD.with.postgresql.model.Job;
import com.example.CRUD.with.postgresql.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String name;

    @NotEmpty
    @Email(message = "email required")
    private String email;

    @NotEmpty
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

    @NotEmpty
    private Department department;

    @NotEmpty
    private Job job;


    private List<Role> roles = new ArrayList<>();
}
