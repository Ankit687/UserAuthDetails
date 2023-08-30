package com.watcix.userauthdetails.entity;

import com.watcix.userauthdetails.repository.RefreshTokenRepository;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.Optional;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;

    public RefreshToken() {
    }

    public RefreshToken(Integer id, String token, Instant expiryDate, UserInfo userInfo) {
        this.id = id;
        this.token = token;
        this.expiryDate = expiryDate;
        this.userInfo = userInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expiryDate=" + expiryDate +
                ", userInfo=" + userInfo +
                '}';
    }

    public static RefreshTokenBuilder builder(RefreshTokenRepository refreshTokenRepository) {
        return new RefreshTokenBuilder(refreshTokenRepository);
    }

    public static final class RefreshTokenBuilder {
        private String token;
        private Instant expiryDate;
        private UserInfo userInfo;

        private RefreshTokenRepository refreshTokenRepository;

        private RefreshTokenBuilder(RefreshTokenRepository refreshTokenRepository) {
            this.refreshTokenRepository = refreshTokenRepository;
        }

        public RefreshTokenBuilder withToken(String token) {
            this.token = token;
            return this;
        }

        public RefreshTokenBuilder withExpiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public RefreshTokenBuilder withUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
            return this;
        }

        public RefreshToken build() {
            RefreshToken refreshToken = dataExits(userInfo);
            refreshToken.setToken(token);
            refreshToken.setExpiryDate(expiryDate);
            refreshToken.setUserInfo(userInfo);
            return refreshToken;
        }

        private RefreshToken dataExits(UserInfo userInfo) {
            Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUserInfo(userInfo);
            return optionalRefreshToken.orElseGet(RefreshToken::new);
        }
    }
}
