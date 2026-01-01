package com.paras.tradeflow.service;

import com.paras.tradeflow.entity.RefreshToken;
import com.paras.tradeflow.entity.User;
import com.paras.tradeflow.repository.RefreshTokenRepository;
import com.paras.tradeflow.repository.UserRepository;
import com.paras.tradeflow.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public RefreshToken create(User user){

        refreshTokenRepository.deleteByUser(user);

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plus(30, ChronoUnit.DAYS));
        return refreshTokenRepository.save(token);
    }

    public RefreshToken verify(String token){

        Optional<RefreshToken> refreshTokenCheck = refreshTokenRepository.findByToken(token);

        if (refreshTokenCheck.isEmpty()){
            String email = jwtService.extractEmail(token);
            User user = userRepository.findByEmail(email).
                    orElseThrow();

            refreshTokenRepository.deleteByUser(user);
            throw new RuntimeException("Refresh Token Reuse Detected");
        }
        RefreshToken refreshToken = refreshTokenCheck.get();
        if(refreshToken.getExpiryDate().isBefore(Instant.now()))
            throw new RuntimeException("Refresh token expired");
        refreshTokenRepository.delete(refreshToken);
        return create(refreshToken.getUser());
    }
}
