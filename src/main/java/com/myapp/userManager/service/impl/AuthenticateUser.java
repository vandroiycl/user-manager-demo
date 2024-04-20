package com.myapp.userManager.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myapp.userManager.entity.UserEntity;
import com.myapp.userManager.repository.PhoneRepository;
import com.myapp.userManager.repository.UserRepository;
import com.myapp.userManager.service.IAuthenticateUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class AuthenticateUser implements IAuthenticateUserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;

    public AuthenticateUser(UserRepository userRepository, PhoneRepository phoneRepository) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
    }

    @Override
    public ResponseEntity<Object> manageUserReq(UserEntity user) {

        if (!validateEmailFormat(user)) {
            return new ResponseEntity<>("Incorrect email format", HttpStatus.BAD_REQUEST);
        }

        if (!validateEmail(user)) {
            return new ResponseEntity<>("email previously registered", HttpStatus.BAD_REQUEST);
        }

        if (!validatePassword(user)) {
            return new ResponseEntity<>("Incorrect password format", HttpStatus.BAD_REQUEST);
        }

        UserEntity updatedUser = createOrUpdateUser(user);

        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String updatedUserJson = objectMapper.writeValueAsString(updatedUser);

            return new ResponseEntity<>(updatedUserJson, HttpStatus.OK);
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
            return new ResponseEntity<>("Error proccessing response", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateEmailFormat(UserEntity user){
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, user.getEmail());
    }

    private boolean validateEmail(UserEntity user){
        return !isEmailRegistered(user.getEmail());
    }

    private boolean isEmailRegistered(String userEmail){
        return userRepository.findByEmail(userEmail) != null;
    }

    private boolean validatePassword(UserEntity user){
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z].*[a-z])(?=.*\\d.*\\d).*$";
        return Pattern.matches(passwordRegex, user.getPassword());
    }

    private UserEntity createOrUpdateUser(UserEntity user) {
        UserEntity existingUser = userRepository.findByEmail(user.getEmail());

        String token = generateToken();

        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setPassword(user.getPassword());
            existingUser.setToken(token);
            existingUser.setActive(true);
            existingUser.setModificationDate(LocalDate.now());
            existingUser.setLastLoginDate(LocalDate.now());
            //TODO: actualizar phones
            return userRepository.save(existingUser);
        } else {
            user.setCreationDate(LocalDate.now());
            user.setModificationDate(LocalDate.now());
            user.setLastLoginDate(LocalDate.now());
            user.setActive(true);
            user.setToken(token);
            phoneRepository.saveAll(user.getPhoneEntities());
            return userRepository.save(user);
        }
    }

    private String generateToken(){
        return UUID.randomUUID().toString();
    }
}

