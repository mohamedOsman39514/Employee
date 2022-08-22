package com.example.CRUD.with.postgresql.repositroy;

import com.example.CRUD.with.postgresql.model.Employee;
import com.example.CRUD.with.postgresql.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, String> {
//    @Query(value = "SELECT token FROM reset_password  WHERE employee_employee_email= :email")
//    PasswordResetToken findByEmail(@Param("email") String email);
}
