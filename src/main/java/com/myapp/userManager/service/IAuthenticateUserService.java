package com.myapp.userManager.service;

import com.myapp.userManager.entity.UserEntity;
import org.springframework.http.ResponseEntity;

public interface IAuthenticateUserService {
    ResponseEntity<Object> manageUserReq(UserEntity user);
}
