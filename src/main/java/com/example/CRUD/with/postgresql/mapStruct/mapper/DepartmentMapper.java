package com.example.CRUD.with.postgresql.mapStruct.mapper;

import com.example.CRUD.with.postgresql.mapStruct.dtos.DepartmentDTO;
import com.example.CRUD.with.postgresql.mapStruct.dtos.ProductDTO;
import com.example.CRUD.with.postgresql.model.Department;
import com.example.CRUD.with.postgresql.model.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    List<DepartmentDTO> toDepartmentDTOs(List<Department> departments);
    DepartmentDTO toDepartmentDTO(Department department);
    Department toDepartment(DepartmentDTO departmentDTO);
}