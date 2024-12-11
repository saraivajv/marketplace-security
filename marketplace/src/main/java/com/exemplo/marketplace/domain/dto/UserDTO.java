package com.exemplo.marketplace.domain.dto;

import com.exemplo.marketplace.domain.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
    @NotBlank(message = "Username is required") String username,
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long") String password,
    UserType userType
) {}
