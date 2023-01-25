package com.onlineshop.cart.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageToCartDto {

    private Long productId;
    private String productName;
    private double productPrice;
    private String image;
    private Long ownerId;
    private String ownerFirstName;
    private String ownerLastName;
}
