package com.example.CRUD.with.postgresql.service;

import com.example.CRUD.with.postgresql.model.Department;
import com.example.CRUD.with.postgresql.model.Product;
import com.example.CRUD.with.postgresql.repositroy.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }
    public Optional<Department> getDepartmentById(long id) {
        return departmentRepository.findById(id);
    }
    public void delete(long id) {
        departmentRepository.deleteById(id);
    }
    public Department save(Department department) {
        return departmentRepository.save(department);
    }


}
