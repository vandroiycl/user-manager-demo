package com.myapp.userManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Getter
@Table(name = "user_data")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_data_seq")
    @SequenceGenerator(name = "user_data_seq", allocationSize = 1)
    private Long id; //TODO: cambiar a UUID

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
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
