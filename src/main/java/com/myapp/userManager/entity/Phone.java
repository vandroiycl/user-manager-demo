package com.myapp.userManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_seq")
    @SequenceGenerator(name = "phone_seq", allocationSize = 1)
    private Long id;
    private String number;
    private String cityCode;
    private String countryCode;
}
