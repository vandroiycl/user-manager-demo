package com.myapp.userManager.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Getter
public class UserEntity {
    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    private LocalDate creationDate;

    private LocalDate modificationDate;

    private LocalDate lastLoginDate;

    private String token;

    private boolean isActive;

    @OneToMany
    @JsonProperty("phones")
    private List<PhoneEntity> phoneEntities;
}
