package com.exemplo.marketplace.repository;

import com.exemplo.marketplace.domain.model.Product;
import com.exemplo.marketplace.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Busca produtos pelo vendedor (seller)
    List<Product> findBySeller(UserEntity seller);

}
