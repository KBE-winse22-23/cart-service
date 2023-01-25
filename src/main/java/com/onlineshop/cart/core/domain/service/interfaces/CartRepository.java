package com.onlineshop.cart.core.domain.service.interfaces;

import com.onlineshop.cart.core.domain.model.Cart;
import com.onlineshop.cart.core.domain.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByOwner(Owner owner);


//    Cart findByProductAndCartOwner(Product product, CartOwner cartOwner);
//    Cart findByProductProductIdAndCartOwnerCartOwnerId(Long productId, Long cartOwnerId);
}
