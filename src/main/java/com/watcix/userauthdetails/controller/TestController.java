package com.watcix.userauthdetails.controller;

import com.watcix.userauthdetails.dto.AuthRequest;
import com.watcix.userauthdetails.service.JwtService;
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
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            if (userInfoService.authenticateUser(authRequest.getUsername(), authRequest.getPassword()))
                return new ResponseEntity<>(jwtService.generateToken(authRequest.getUsername()), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            System.out.println(e);
        }
        return new ResponseEntity<>("Invalid user request", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
