package com.exemplo.marketplace.repository;

import com.exemplo.marketplace.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Verifica se um usuário existe pelo username
    boolean existsByUsername(String username);

    // Encontra um usuário por username
    Optional<UserEntity> findByUsername(String username);
}
