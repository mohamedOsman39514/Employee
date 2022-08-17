package com.example.CRUD.with.postgresql.repositroy;

import com.example.CRUD.with.postgresql.model.Job;
import com.example.CRUD.with.postgresql.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
