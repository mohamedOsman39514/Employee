package com.example.CRUD.with.postgresql.service;

import com.example.CRUD.with.postgresql.mapStruct.dtos.EmployeeDTO;
import com.example.CRUD.with.postgresql.model.Employee;
import com.example.CRUD.with.postgresql.model.Role;
import com.example.CRUD.with.postgresql.repositroy.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class EmployeeService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee =employeeRepository.findById(username).get();
        if (employee == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }



        return new User(employee.getEmail(), employee.getPassword(), getAuthorities(employee.getRoles()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getName();
        }

        return authorities;
    }



    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public Optional<Employee> getEmployeeByEmail(String id) {
        return employeeRepository.findById(id);
    }
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    public void delete(String id) {
        employeeRepository.deleteById(id);
    }
    public void update(Employee employee) {
        employeeRepository.save(employee);
    }
}
