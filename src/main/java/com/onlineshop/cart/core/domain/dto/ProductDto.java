package com.onlineshop.cart.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
    private Long productId;
    private String productName;
    private double productPrice;
    private String image;
    private int productQuantity;
}
