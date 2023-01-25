package com.onlineshop.cart.core.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductMapId implements Serializable {
    private Long cartId;
    private Long productId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartProductMapId)) return false;
        CartProductMapId cartProductMapId = (CartProductMapId) o;
        return getCartId().equals(cartProductMapId.getCartId()) && getProductId().equals(cartProductMapId.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCartId(), getProductId());
    }
}
