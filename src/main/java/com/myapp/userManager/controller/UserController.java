package com.myapp.userManager.controller;

import com.myapp.userManager.entity.UserEntity;
import com.myapp.userManager.service.IAuthenticateUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final IAuthenticateUserService authenticateUserService;

    public UserController(IAuthenticateUserService authenticateUserService) {
        this.authenticateUserService = authenticateUserService;
    }

    @PostMapping("/user/auth")
    public ResponseEntity<Object> authenticateUser(@RequestBody UserEntity user) {
        return authenticateUserService.manageUserReq(user);
    }
}
