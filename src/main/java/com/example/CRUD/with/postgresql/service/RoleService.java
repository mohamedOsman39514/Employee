package com.example.CRUD.with.postgresql.service;

import com.example.CRUD.with.postgresql.model.Product;
import com.example.CRUD.with.postgresql.model.Role;
import com.example.CRUD.with.postgresql.repositroy.ProductRespository;
import com.example.CRUD.with.postgresql.repositroy.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> findAll()
    {
        return roleRepository.findAll();
    }

    public Optional<Role> findById(Long id)
    {
        return roleRepository.findById(id);
    }

    public Role save(Role role)
    {
        return roleRepository.save(role);
    }

    public void deleteById(Long id)
    {
        roleRepository.deleteById(id);
    }
}
