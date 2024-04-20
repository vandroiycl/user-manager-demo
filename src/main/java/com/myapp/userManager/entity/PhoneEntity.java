package com.myapp.userManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_seq")
    @SequenceGenerator(name = "phone_seq", allocationSize = 1)
    @JsonIgnore
    private Long id;

    @JsonProperty("number")
    private String number;

    @JsonProperty("cityCode")
    private String cityCode;

    @JsonProperty("countryCode")
    private String countryCode;
}
