package com.watcix.userauthdetails.controller;

import com.watcix.userauthdetails.dto.AuthRequest;
import com.watcix.userauthdetails.dto.JwtResponse;
import com.watcix.userauthdetails.dto.RefreshTokenRequest;
import com.watcix.userauthdetails.entity.RefreshToken;
import com.watcix.userauthdetails.service.JwtService;
import com.watcix.userauthdetails.service.RefreshTokenService;
import com.watcix.userauthdetails.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @GetMapping("/")
    public ResponseEntity<String> getHomePage() {
        return new ResponseEntity<>("Home Page", HttpStatus.OK);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> getHelloWorld() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> getId(@PathVariable int id) {
        return new ResponseEntity<>("Your id number is " + id, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            if (userInfoService.authenticateUser(authRequest.getUsername(), authRequest.getPassword())) {
                String generatedToken = jwtService.generateToken(authRequest.getUsername());
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
                JwtResponse jwtResponse = JwtResponse.builder()
                        .withAccessToken(generatedToken)
                        .withToken(refreshToken.getToken())
                        .build();
                return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
            }
        } catch (UsernameNotFoundException e) {
            System.out.println(e);
        }
        return new ResponseEntity<>("Invalid user request", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String generatedToken = jwtService.generateToken(userInfo.getName());
                    return JwtResponse.builder()
                            .withAccessToken(generatedToken)
                            .withToken(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}
