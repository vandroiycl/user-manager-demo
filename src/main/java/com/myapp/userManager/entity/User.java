package com.myapp.userManager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "user_data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_data_seq")
    @SequenceGenerator(name = "phone_seq", allocationSize = 1)
    private String id; //TODO: cambiar a UUID
    private String name;
    private String email; //TODO: validar regexp, y no duplicados
    private String password;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private LocalDate lastLoginDate;
    //TODO: un JWT asociado al usuario
    private boolean isActive;
    @OneToMany
    private List<Phone> phones;
}
