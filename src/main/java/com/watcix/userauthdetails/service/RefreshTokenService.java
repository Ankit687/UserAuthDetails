package com.watcix.userauthdetails.service;

import com.watcix.userauthdetails.entity.RefreshToken;
import com.watcix.userauthdetails.repository.RefreshTokenRepository;
import com.watcix.userauthdetails.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder(refreshTokenRepository)
                .withUserInfo(userInfoRepository.findByName(username).get())
                .withToken(UUID.randomUUID().toString())
                .withExpiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + "Refresh token was expired. Please make a new signin request");
        }
        return refreshToken;
    }
}
