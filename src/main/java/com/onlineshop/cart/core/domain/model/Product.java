package com.onlineshop.cart.core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Product {

    @Id
    private Long productId;
    private String productName;
    private double productPrice;
    private String image;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartProductMap> cartProductMaps = new LinkedList<>();


}
