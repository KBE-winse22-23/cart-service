package com.onlineshop.cart.core.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "owner_id",
            referencedColumnName = "ownerId"
    )
    private Owner owner;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "product_cart_map",
//            joinColumns = @JoinColumn(
//                    name = "cart_id",
//                    referencedColumnName = "cartId"
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "product_id",
//                    referencedColumnName = "productId"
//            )
//    )
//    private List<Product> products;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartProductMap> cartProductMaps = new LinkedList<>();


}
