package com.example.CRUD.with.postgresql.mapStruct.mapper;


import com.example.CRUD.with.postgresql.mapStruct.dtos.RoleDTO;
import com.example.CRUD.with.postgresql.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    List<RoleDTO> toRoleDTOs(List<Role> roles);
    Role toRole(RoleDTO roleDTO);
    RoleDTO toRoleDTO(Role role);
}
