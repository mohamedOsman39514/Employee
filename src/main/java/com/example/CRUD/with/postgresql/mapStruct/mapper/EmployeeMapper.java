package com.example.CRUD.with.postgresql.mapStruct.mapper;

import com.example.CRUD.with.postgresql.mapStruct.dtos.DepartmentDTO;
import com.example.CRUD.with.postgresql.mapStruct.dtos.EmployeeDTO;
import com.example.CRUD.with.postgresql.mapStruct.dtos.ProductDTO;
import com.example.CRUD.with.postgresql.model.Department;
import com.example.CRUD.with.postgresql.model.Employee;
import com.example.CRUD.with.postgresql.model.Product;
import org.mapstruct.Mapper;


import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

//    List<EmployeeDTO> toEmployeeDTOs(List<Employee> employees);
//    Employee toEmployee(EmployeeDTO employeeDTO);
//    EmployeeDTO toEmployeeDTO(Employee employee);



    public static List<EmployeeDTO> toEmployeeDTOs(List<Employee> employees) {
        return employees.stream().map(employee -> toEmployeeDTO(employee)).collect(Collectors.toList());
    }

    public static Employee toEmployee(EmployeeDTO employeeDTO){
        if(employeeDTO == null){
            return null;
        }
        return Employee.builder()
                .name(employeeDTO.getName())
                .password(employeeDTO.getPassword())
                .email(employeeDTO.getEmail())
                .job(employeeDTO.getJob())
                .roles(employeeDTO.getRoles())
                .department(employeeDTO.getDepartment())
                .build();
    }

    public static EmployeeDTO toEmployeeDTO(Employee employee){
        if(employee == null){
            return null;
        }

        return EmployeeDTO.builder()
                .name(employee.getName())
                .password(employee.getPassword())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .job(employee.getJob())
                .roles(employee.getRoles())
                .build();
    }
}
