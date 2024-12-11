package com.exemplo.marketplace.controller;

import com.exemplo.marketplace.domain.dto.UserDTO;
import com.exemplo.marketplace.domain.model.UserEntity;
import com.exemplo.marketplace.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Cadastro de usuário
    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            UserEntity user = userService.registerUser(userDTO);
            return ResponseEntity.ok(user);  // Retorna o usuário cadastrado
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(null);  // Ou pode retornar uma mensagem de erro
        }
    }
}
