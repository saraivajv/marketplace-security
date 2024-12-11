package com.exemplo.marketplace.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductDTO(
    @NotBlank(message = "Product name is required") String name,
    @NotBlank(message = "Product description is required") String description,
    @Positive(message = "Price must be a positive value") double price
) {}
