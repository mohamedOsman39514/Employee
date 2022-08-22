package com.example.CRUD.with.postgresql.rest.mapper;

import com.example.CRUD.with.postgresql.rest.dtos.EmployeeDTO;
import com.example.CRUD.with.postgresql.model.Employee;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    List<EmployeeDTO> toEmployeeDTOs(List<Employee> employees);
    Employee toEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO toEmployeeDTO(Employee employee);
}
