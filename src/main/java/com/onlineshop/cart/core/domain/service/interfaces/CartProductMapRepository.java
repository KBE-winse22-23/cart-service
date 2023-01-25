package com.onlineshop.cart.core.domain.service.interfaces;

import com.onlineshop.cart.core.domain.model.Cart;
import com.onlineshop.cart.core.domain.model.CartProductMap;
import com.onlineshop.cart.core.domain.model.CartProductMapId;
import com.onlineshop.cart.core.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductMapRepository extends JpaRepository<CartProductMap, CartProductMapId> {

    CartProductMap findByCartAndProduct(Cart cart, Product product);
}
