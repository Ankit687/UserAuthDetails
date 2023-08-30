package com.watcix.userauthdetails.repository;

import com.watcix.userauthdetails.entity.RefreshToken;
import com.watcix.userauthdetails.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserInfo(UserInfo userInfo);

    Optional<RefreshToken> findByTokenAndUserInfo(String token, UserInfo userInfo);
}
