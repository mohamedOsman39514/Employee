package com.example.CRUD.with.postgresql.repositroy;

import com.example.CRUD.with.postgresql.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository  extends JpaRepository<Job, Long> {
}
