package com.myapp.userManager.service.impl;

import com.myapp.userManager.entity.PhoneEntity;
import com.myapp.userManager.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class AuthenticateUserTest {

    private final AuthenticateUser authenticateUser;

    @Autowired
    AuthenticateUserTest(AuthenticateUser authenticateUser) {
        this.authenticateUser = authenticateUser;
    }

    @Test
    void givenInvalidEmail_returnFalse() {
        UserEntity user = createUser();
        user.setEmail("notAnEmail");

        Assertions.assertFalse(authenticateUser.validateEmailFormat(user));
    }

    @Test
    void givenValidEmail_returnTrue() {
        UserEntity user = createUser();
        user.setEmail("valid@mail.com");

        Assertions.assertTrue(authenticateUser.validateEmailFormat(user));
    }

    @Test
    void givenInvalidPassword_returnFalse() {
        UserEntity user = createUser();
        user.setPassword("123456");

        Assertions.assertFalse(authenticateUser.validatePassword(user));
    }

    private UserEntity createUser() {
        UserEntity user = new UserEntity();
        user.setName("Juan Rodriguez");
        user.setEmail("juan@rodriguez.org");
        user.setPassword("Hunter21");

        PhoneEntity phone = new PhoneEntity();
        phone.setNumber("1234567");
        phone.setCityCode("1");
        phone.setCountryCode("57");
        user.setPhoneEntities(Collections.singletonList(phone));

        return user;
    }
}