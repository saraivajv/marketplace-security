package com.exemplo.marketplace.service;

import com.exemplo.marketplace.domain.model.Product;
import com.exemplo.marketplace.domain.model.UserEntity;
import com.exemplo.marketplace.repository.ProductRepository;
import com.exemplo.marketplace.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Método para registrar um produto
    public Product registerProduct(Product product) {
        // Aqui podemos fazer verificações antes de salvar, como validações extras, etc.
        return productRepository.save(product);
    }

 // Método para comprar um produto
    public String buyProduct(Long productId, String username) {
        // Verifica se o produto existe
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            return "Product not found";
        }

        Product product = productOpt.get();

        // Verifica se o produto já foi comprado
        if (product.isSold()) {
            return "Product already sold";
        }

        // Busca o usuário (buyer) pelo username
        Optional<UserEntity> buyerOpt = userRepository.findByUsername(username);
        if (buyerOpt.isEmpty()) {
            return "Buyer not found";
        }

        UserEntity buyer = buyerOpt.get();  // Usuário logado que é o comprador

        // Marca o produto como vendido e associa o comprador
        product.setSold(true);
        product.setBuyer(buyer);  // Associa o comprador ao produto

        // Salva o produto atualizado no banco de dados
        productRepository.save(product);

        return "Product purchased successfully";
    }

    // Método para obter um produto pelo ID (caso seja necessário no serviço)
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

 // Método para registrar um produto associado ao SELLER
    public Product registerProductForSeller(Product product, String username) {
        // Buscar o usuário pelo username
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // Verifica se o usuário é um SELLER
            if (user.getUserType().toString().equals("SELLER")) {
                // Associa o SELLER ao produto
                product.setSeller(user);
                return productRepository.save(product);  // Salva o produto no banco
            } else {
                throw new RuntimeException("Usuário não é um SELLER.");
            }
        } else {
            throw new RuntimeException("Usuário não encontrado.");
        }
    }
}
