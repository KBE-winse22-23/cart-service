package com.onlineshop.cart.port.user.controller;


import com.onlineshop.cart.core.domain.dto.CartProductMapDto;
import com.onlineshop.cart.core.domain.dto.OwnerByEmail;
import com.onlineshop.cart.core.domain.dto.ProductDto;
import com.onlineshop.cart.core.domain.model.Cart;
import com.onlineshop.cart.core.domain.model.CartProductMap;
import com.onlineshop.cart.core.domain.model.Owner;
import com.onlineshop.cart.core.domain.model.Product;
import com.onlineshop.cart.core.domain.service.impl.CartService;
import com.onlineshop.cart.port.user.exception.EmptyFieldException;
import com.onlineshop.cart.port.user.exception.NotFoundException;
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
    public List<Cart> getCarts(){
        return cartService.getCarts();
    }

    @PostMapping("/create-cart")
    public Cart createCartForUser(@RequestBody Owner owner) throws EmptyFieldException {
        return cartService.createCartForUser(owner);
    }

    @PostMapping("/add-to-cart/{cartId}")
    public Product addProductToAnExistingCart(@RequestBody Product product, @PathVariable Long cartId) throws NotFoundException {
            return cartService.addProductToAnExistingCart(product, cartId);

    }


    @PostMapping("/find-by-owner")
    public Cart findCartByOwner(@RequestBody Owner owner) throws NotFoundException {
       return cartService.findByOwner(owner);
    }

    @GetMapping("/{cartId}")
    public List<ProductDto> getProductsFromCart(@PathVariable("cartId") Long cartId) throws NotFoundException {
        return cartService.getProductsFromCart(cartId);
    }

    @GetMapping("/count-products/{cartId}")
    public int countProductsInCart(@PathVariable("cartId") Long cartId) throws NotFoundException {
        return cartService.countProductsInCart(cartId);
    }

    @DeleteMapping("/remove-product")
    public boolean removeProductFromCart(@RequestBody CartProductMapDto cartProductMapDto) throws NotFoundException {
        return cartService.removeProductFromCart(cartProductMapDto);
    }

    @PutMapping("/increase-quantity")
    public CartProductMap increaseProductQuantity(@RequestBody CartProductMapDto cartProductMapDto) throws NotFoundException {
        return cartService.increaseProductQuantity(cartProductMapDto);
    }

    @PutMapping("/decrease-quantity")
    public CartProductMap decreaseProductQuantity(@RequestBody CartProductMapDto cartProductMapDto) throws NotFoundException {
        return cartService.decreaseProductQuantity(cartProductMapDto);
    }

    @GetMapping("/subtotal/{cartId}")
    public double cartSubtotal(@PathVariable("cartId") Long cartId) throws NotFoundException {
        return cartService.cartSubtotal(cartId);
    }

    @PostMapping("/find-by-owner-email")
    public Cart findCartByOwnerEmail(@RequestBody OwnerByEmail email) throws NotFoundException {
        return cartService.findByOwnerEmail(email.getEmail());
    }

    @DeleteMapping("/remove-products/{cartId}")
    public boolean removeProducts(@PathVariable("cartId") Long cartId) throws NotFoundException {
        return cartService.removeProducts(cartId);
    }
}
