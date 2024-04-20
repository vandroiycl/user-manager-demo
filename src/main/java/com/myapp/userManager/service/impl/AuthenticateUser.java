package com.myapp.userManager.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myapp.userManager.entity.UserEntity;
import com.myapp.userManager.repository.PhoneRepository;
import com.myapp.userManager.repository.UserRepository;
import com.myapp.userManager.service.IAuthenticateUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
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

        Map<String, String> responseMap = new HashMap<>();

        if (!validateEmailFormat(user)) {
            responseMap.put("mensaje", "Formato de correo incorrecto");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }

        if (!validateEmail(user)) {
            responseMap.put("mensaje", "Correo ya registrado");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }

        if (!validatePassword(user)) {
            responseMap.put("mensaje", "Formato de contraseña incorrecto");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }

        UserEntity updatedUser = createOrUpdateUser(user);

        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String updatedUserJson = objectMapper.writeValueAsString(updatedUser);

            return new ResponseEntity<>(updatedUserJson, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.debug("Json Error -> {} ", e.getMessage());
            responseMap.put("mensaje", "Error al procesar la respuesta");
            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    boolean validateEmailFormat(UserEntity user) {
        if (user.getEmail() != null && user.getEmail().length() > 5) {
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            return Pattern.matches(emailRegex, user.getEmail());
        } else {
            return false;
        }
    }

    boolean validateEmail(UserEntity user) {
        return !isEmailRegistered(user.getEmail());
    }

    boolean isEmailRegistered(String userEmail) {
        return userRepository.findByEmail(userEmail) != null;
    }

    boolean validatePassword(UserEntity user) {
        if (user.getPassword() != null && user.getPassword().length() > 3) {
            String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z].*[a-z])(?=.*\\d.*\\d).*$";
            return Pattern.matches(passwordRegex, user.getPassword());
        } else {
            return false;
        }
    }

    UserEntity createOrUpdateUser(UserEntity user) {
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
            user.setId(UUID.randomUUID().toString());
            user.setCreationDate(LocalDate.now());
            user.setModificationDate(LocalDate.now());
            user.setLastLoginDate(LocalDate.now());
            user.setActive(true);
            user.setToken(token);
            phoneRepository.saveAll(user.getPhoneEntities());
            return userRepository.save(user);
        }
    }

    String generateToken() {
        return UUID.randomUUID().toString();
    }
}

