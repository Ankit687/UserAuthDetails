package com.watcix.userauthdetails.controller;

import com.watcix.userauthdetails.entity.UserInfo;
import com.watcix.userauthdetails.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/saveNewUser")
    public ResponseEntity<UserInfo> saveUserInfo(@RequestBody UserInfo userInfo) {
        return new ResponseEntity<>(userInfoService.addUser(userInfo), HttpStatus.OK);
    }
}
