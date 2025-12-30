package com.paras.tradeflow.service;

import com.paras.tradeflow.entity.RefreshToken;
import com.paras.tradeflow.entity.User;
import com.paras.tradeflow.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repo;

    public RefreshToken create(User user){
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plus(30, ChronoUnit.DAYS));
        return repo.save(token);
    }

    public RefreshToken verify(String token){
        RefreshToken refreshToken = repo.findByToken(token).
                orElseThrow(()-> new RuntimeException("Invalid refresh token"));

        if(refreshToken.getExpiryDate().isBefore(Instant.now()))
            throw new RuntimeException("Refresh token expird");

        return refreshToken;
    }
}
