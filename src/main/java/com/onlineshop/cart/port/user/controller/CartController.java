package com.onlineshop.cart.port.user.controller;


import com.onlineshop.cart.core.domain.model.Cart;
import com.onlineshop.cart.core.domain.model.Owner;
import com.onlineshop.cart.core.domain.model.Product;
import com.onlineshop.cart.core.domain.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;



    @GetMapping
    public List<Cart> viewAllCarts(){
        return cartService.findAll();
    }

    @PostMapping("/create-cart")
    public Cart createCart(@RequestBody Owner owner){
        if(owner==null){
            throw new NullPointerException("Owner is empty. Can't create a shopping cart");
        }
        return cartService.createCart(owner);
    }

    @PostMapping("/add-to-cart/{cartId}")
    public Product addProductToCart(@RequestBody Product product, @PathVariable Long cartId){
            return cartService.addProductToCart(product, cartId);

    }


    @GetMapping("/find-by-owner")
    public Cart findCartByOwner(@RequestBody Owner owner){
       return cartService.findByOwner(owner);
    }
}
