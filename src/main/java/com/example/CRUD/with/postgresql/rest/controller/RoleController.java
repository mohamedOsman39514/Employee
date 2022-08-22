package com.example.CRUD.with.postgresql.rest.controller;

import com.example.CRUD.with.postgresql.rest.exception.ResourceNotFound;
import com.example.CRUD.with.postgresql.rest.dtos.RoleDTO;
import com.example.CRUD.with.postgresql.model.Role;
import com.example.CRUD.with.postgresql.rest.mapper.RoleMapperImpl;
import com.example.CRUD.with.postgresql.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v4/roles")
@Tag( name = "Roles",description ="Rest Api For Roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapperImpl roleMapper;

    @GetMapping
    @Operation(summary = "This method is used to get all roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roleDTOS = roleMapper.toRoleDTOs(roleService.findAll());
        return ResponseEntity.ok(roleDTOS);
    }

    @PostMapping
    @Operation(summary = "This method is used to create role")
    public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO roleDTO) {
        Role role = roleMapper.toRole(roleDTO);
        roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "This method is used to get role")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable(value = "id") Long id)
            throws ResourceNotFound {
        Optional<Role> role = roleService.findById(id);
        RoleDTO roleDTO = roleMapper.toRoleDTO(role.get());
        return ResponseEntity.ok(roleDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "This method is used to update role")
    public ResponseEntity<RoleDTO> update(@PathVariable Long id,	@RequestBody RoleDTO roleDTO) throws ResourceNotFound {
        Role role = roleMapper.toRole(roleDTO);
        Role roleId = roleService.findById(id)
                .orElseThrow(()->new ResourceNotFound("Role Not Found"));
        role.setId(id);
        role.setName(role.getName()!=null ? role.getName() : roleId.getName());
        roleService.save(role);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "This method is used to delete role")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
