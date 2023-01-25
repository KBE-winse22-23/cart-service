package com.onlineshop.cart.core.domain.service.interfaces;

import com.onlineshop.cart.core.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
