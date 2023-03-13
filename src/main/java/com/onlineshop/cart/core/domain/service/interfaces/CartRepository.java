package com.onlineshop.cart.core.domain.service.interfaces;

import com.onlineshop.cart.core.domain.model.Cart;
import com.onlineshop.cart.core.domain.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByOwner(Owner owner);
    Optional<Cart> findByOwnerEmail(String email);


//    Cart findByProductAndCartOwner(Product product, CartOwner cartOwner);
//    Cart findByProductProductIdAndCartOwnerCartOwnerId(Long productId, Long cartOwnerId);
}
