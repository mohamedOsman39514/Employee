package com.example.CRUD.with.postgresql.controller;

import com.example.CRUD.with.postgresql.filter.JwtFilter;
import com.example.CRUD.with.postgresql.model.Employee;
import com.example.CRUD.with.postgresql.service.EmployeeService;
import com.example.CRUD.with.postgresql.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@RestController
public class RestrictController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtUtil jwtUtil;

    public boolean getPermission(String role){

        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        Employee employee = employeeService.getEmployeeByEmail(username).get();
        List<?> x = employee.getRoles().stream().map(p ->p.getName()).collect(Collectors.toList());

        if(x.contains(role)) return true;
        else return false;
    }

}
