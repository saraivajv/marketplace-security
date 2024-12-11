package com.exemplo.marketplace.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;

    @Column(nullable = false)
    private boolean sold = false;  // Marca se o produto foi vendido

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private UserEntity buyer;  // Referência ao BUYER que comprou o produto, se aplicável

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private UserEntity seller;  // Referência ao SELLER que vendeu o produto
}
