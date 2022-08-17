package com.example.CRUD.with.postgresql.controller;

import com.example.CRUD.with.postgresql.exception.ResourceNotFound;
import com.example.CRUD.with.postgresql.filter.JwtFilter;
import com.example.CRUD.with.postgresql.mapStruct.dtos.EmployeeDTO;
import com.example.CRUD.with.postgresql.mapStruct.mapper.EmployeeMapper;
import com.example.CRUD.with.postgresql.model.Employee;
import com.example.CRUD.with.postgresql.service.EmployeeService;
import com.example.CRUD.with.postgresql.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private EmployeeMapper employeeMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RestrictController restrictController;

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        if (restrictController.getPermission("manger")) {
            List<EmployeeDTO> employeeDTOList = EmployeeMapper.toEmployeeDTOs(employeeService.getAllEmployees());
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTOList);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not have permission");
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getDepartmentById(@PathVariable String id)
            throws ResourceNotFound {
        Employee employee = employeeService.getEmployeeByEmail(id)
                .orElseThrow(() -> new ResourceNotFound("Employee Not Found"));
        ;
        EmployeeDTO employeeDTO = EmployeeMapper.toEmployeeDTO(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.toEmployee(employeeDTO);
        employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTO);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable String id, @RequestBody EmployeeDTO employeeDTO) throws ResourceNotFound {
        Employee employee = EmployeeMapper.toEmployee(employeeDTO);

        Employee employeeId = employeeService.getEmployeeByEmail(id)
                .orElseThrow(() -> new ResourceNotFound("Employee Not Found"));

        employee.setEmail(id);
        employee.setName(employee.getName() != null ? employee.getName() : employeeId.getName());
        employee.setPassword(employee.getPassword() != null ? employee.getPassword() : employeeId.getPassword());
        employee.setDepartment(employee.getDepartment() != null ? employee.getDepartment() : employeeId.getDepartment());
        employee.setJob(employee.getJob() != null ? employee.getJob() : employeeId.getJob());
        employee.setRoles(employee.getRoles() != null ? employee.getRoles() : employeeId.getRoles());
        employeeService.update(employee);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeDTO);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        employeeService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
