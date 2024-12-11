package com.exemplo.marketplace.controller;

import com.exemplo.marketplace.domain.model.Product;
import com.exemplo.marketplace.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Endpoint para registrar um produto, acessível apenas para SELLER
    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/register")
    public ResponseEntity<Product> registerProduct(@RequestBody Product product) {
        try {
            // Obtém a autenticação do usuário logado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Obtém o nome do usuário logado (que é o username)
            String username = authentication.getName();

            // Chama o service para registrar o produto, passando o username
            Product savedProduct = productService.registerProductForSeller(product, username);
            return ResponseEntity.ok(savedProduct);  // Retorna o produto salvo
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(null);  // Retorna erro 400 caso algo dê errado
        }
    }

    // Endpoint para visualizar um produto, acessível por BUYER ou SELLER
    @PreAuthorize("hasRole('BUYER')")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        // Chama o service para obter o produto
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para BUYER comprar um produto
    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/{id}/buy")
    public ResponseEntity<String> buyProduct(@PathVariable Long id) {
        // Obtém a autenticação do usuário logado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Obtém o nome do usuário logado (que é o username)
        String username = authentication.getName();

        // Chama o service para comprar o produto, passando o username do comprador
        String result = productService.buyProduct(id, username);

        if (result.equals("Product purchased successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }
}
