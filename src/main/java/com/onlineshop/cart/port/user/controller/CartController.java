package com.onlineshop.cart.port.user.controller;


import com.onlineshop.cart.core.domain.dto.CartProductMapDto;
import com.onlineshop.cart.core.domain.dto.ProductDto;
import com.onlineshop.cart.core.domain.model.Cart;
import com.onlineshop.cart.core.domain.model.CartProductMap;
import com.onlineshop.cart.core.domain.model.Owner;
import com.onlineshop.cart.core.domain.model.Product;
import com.onlineshop.cart.core.domain.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
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


    @PostMapping("/find-by-owner")
    public Cart findCartByOwner(@RequestBody Owner owner){
       return cartService.findByOwner(owner);
    }

    @GetMapping("/{cartId}")
    public List<ProductDto> getProductsFromCart(@PathVariable("cartId") Long cartId) {
        return cartService.getProductsFromCart(cartId);
    }

    @GetMapping("/count-products/{cartId}")
    public int countProductsInCart(@PathVariable("cartId") Long cartId){
        return cartService.countProductsInCart(cartId);
    }

    @DeleteMapping("/remove-product")
    public boolean removeProductFromCart(@RequestBody CartProductMapDto cartProductMapDto){
        return cartService.removeProductFromCart(cartProductMapDto);
    }

//    public Cart incrementProductQuantity(Cart cart, Product product){
//        return cartService.incrementProductQuantity(cart, product);
//    }
}
