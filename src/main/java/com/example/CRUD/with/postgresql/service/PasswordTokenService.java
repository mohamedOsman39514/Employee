package com.example.CRUD.with.postgresql.service;

import com.example.CRUD.with.postgresql.model.Employee;
import com.example.CRUD.with.postgresql.model.Job;
import com.example.CRUD.with.postgresql.model.PasswordResetToken;
import com.example.CRUD.with.postgresql.repositroy.PasswordTokenRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordTokenService {

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    public void createPasswordResetTokenForUser(Employee user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodeToken = passwordEncoder.encode(token);

        myToken.setEmployee(user);
        myToken.setToken(token);
        myToken.setExpiryDate(new Date(System.currentTimeMillis() + (2 * 60* 1000)));
        passwordTokenRepository.save(myToken);
    }

    public Optional<PasswordResetToken> getResetToken(String id) {
        return passwordTokenRepository.findById(id);
    }
}
