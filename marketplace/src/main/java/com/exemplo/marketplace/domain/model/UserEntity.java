package com.exemplo.marketplace.domain.model;

import com.exemplo.marketplace.domain.enums.UserType;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType; // Tipo de usuário (BUYER, SELLER)
}
