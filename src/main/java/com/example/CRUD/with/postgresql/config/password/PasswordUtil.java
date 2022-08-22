package com.example.CRUD.with.postgresql.config.password;

import com.example.CRUD.with.postgresql.model.PasswordResetToken;
import com.example.CRUD.with.postgresql.repositroy.PasswordTokenRepository;
import com.example.CRUD.with.postgresql.service.PasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
@Component
public class PasswordUtil {

    @Autowired
    private PasswordTokenService passwordTokenService;

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenService.getResetToken(token).get();

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
