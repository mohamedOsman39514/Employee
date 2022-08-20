package com.example.CRUD.with.postgresql.controller;

import com.example.CRUD.with.postgresql.exception.ResourceNotFound;
import com.example.CRUD.with.postgresql.filter.JwtFilter;
import com.example.CRUD.with.postgresql.mapStruct.dtos.EmployeeDTO;
import com.example.CRUD.with.postgresql.mapStruct.mapper.EmployeeMapper;
import com.example.CRUD.with.postgresql.mapStruct.mapper.EmployeeMapperImpl;
import com.example.CRUD.with.postgresql.model.Employee;
import com.example.CRUD.with.postgresql.model.Role;
import com.example.CRUD.with.postgresql.service.EmployeeService;
import com.example.CRUD.with.postgresql.util.JwtUtil;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v2/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapperImpl employeeMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
            List<EmployeeDTO> employeeDTOList = employeeMapper.toEmployeeDTOs(employeeService.getAllEmployees());
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getDepartmentById(@PathVariable String id)
            throws ResourceNotFound {
        Employee employee = employeeService.getEmployeeByEmail(id)
                .orElseThrow(() -> new ResourceNotFound("Employee Not Found"));
        ;
        EmployeeDTO employeeDTO = employeeMapper.toEmployeeDTO(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEmployee(employeeDTO);
        employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTO);
    }

    @PatchMapping("/updatePassword/{newPassword}")
    public ResponseEntity<?> changeUserPassword(@RequestBody Employee employee,@PathVariable String newPassword) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employeeId = employeeService.getEmployeeByEmail(email).get();
        BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

        if(passwordEncoder.matches(employee.getPassword(),employeeId.getPassword())){
            employeeService.updatePassword(email,newPassword);
            return ResponseEntity.status(200).body("successfully updated......");
        }
        else return ResponseEntity.status(403).body("incorrect password");
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable String id, @RequestBody EmployeeDTO employeeDTO) throws ResourceNotFound {
        Employee employee = employeeMapper.toEmployee(employeeDTO);

        Employee employeeId = employeeService.getEmployeeByEmail(id)
                .orElseThrow(() -> new ResourceNotFound("Employee Not Found"));

        employee.setEmail(id);
        employee.setName(employee.getName() != null ? employee.getName() : employeeId.getName());
        employee.setPassword(employeeId.getPassword());
        employee.setDepartment(employee.getDepartment() != null ? employee.getDepartment() : employeeId.getDepartment());
        employee.setJob(employee.getJob() != null ? employee.getJob() : employeeId.getJob());
        employee.setRoles(employee.getRoles() != null ? employee.getRoles() : employeeId.getRoles());
        employeeService.update(employee);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        employeeService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
