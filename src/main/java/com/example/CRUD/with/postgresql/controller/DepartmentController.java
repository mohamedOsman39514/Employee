package com.example.CRUD.with.postgresql.controller;

import com.example.CRUD.with.postgresql.exception.ResourceNotFound;
import com.example.CRUD.with.postgresql.mapStruct.dtos.DepartmentDTO;
import com.example.CRUD.with.postgresql.mapStruct.mapper.DepartmentMapper;
import com.example.CRUD.with.postgresql.mapStruct.mapper.DepartmentMapperImpl;
import com.example.CRUD.with.postgresql.model.Department;
import com.example.CRUD.with.postgresql.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentMapperImpl departmentMapper;

    @GetMapping("/department")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departmentDTOList = departmentMapper.toDepartmentDTOs(departmentService.getAllDepartment());
        return ResponseEntity.ok(departmentDTOList);
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id)
            throws ResourceNotFound {
        Optional<Department> department = departmentService.getDepartmentById(id);
        DepartmentDTO departmentDTO = departmentMapper.toDepartmentDTO(department.get());
        return ResponseEntity.ok(departmentDTO);
    }

    @PostMapping("/departments")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toDepartment(departmentDTO);
        departmentService.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentDTO);
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<DepartmentDTO> update(@PathVariable Long id,	@RequestBody DepartmentDTO departmentDTO) throws ResourceNotFound {
        Department department = departmentMapper.toDepartment(departmentDTO);
        Department departmentId = departmentService.getDepartmentById(id)
                .orElseThrow(()->new ResourceNotFound("Department Not Found"));
        department.setId(id);
        department.setName(department.getName()!=null ? department.getName() : departmentId.getName());
        departmentService.save(department);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
