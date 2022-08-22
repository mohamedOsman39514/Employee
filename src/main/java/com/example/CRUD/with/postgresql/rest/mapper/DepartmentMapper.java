package com.example.CRUD.with.postgresql.rest.mapper;

import com.example.CRUD.with.postgresql.rest.dtos.DepartmentDTO;
import com.example.CRUD.with.postgresql.model.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    List<DepartmentDTO> toDepartmentDTOs(List<Department> departments);
    DepartmentDTO toDepartmentDTO(Department department);
    Department toDepartment(DepartmentDTO departmentDTO);
}