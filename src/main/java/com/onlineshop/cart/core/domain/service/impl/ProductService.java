package com.onlineshop.cart.core.domain.service.impl;

import com.onlineshop.cart.core.domain.model.Product;
import com.onlineshop.cart.core.domain.service.interfaces.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product){
        Optional<Product> findProduct = productRepository.findById(product.getProductId());

        return findProduct.orElseGet(() -> productRepository.save(product));
    }
}
