package com.onlineshop.cart.core.domain.service.impl;


import com.onlineshop.cart.core.domain.dto.SendMessageToCartDto;
import com.onlineshop.cart.core.domain.model.*;
import com.onlineshop.cart.core.domain.service.interfaces.CartProductMapRepository;
import com.onlineshop.cart.core.domain.service.interfaces.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartProductMapRepository cartProductMapRepository;

    @Autowired
    private ProductService productService;


    public List<Cart> viewAllCarts() {
        return cartRepository.findAll();
    }

    public Cart createCart(Owner owner) {
        Cart cart = new Cart();
        cart.setOwner(owner);
        cartRepository.save(cart);
        return cart;
    }



    public Product addProductToCart(Product product, Long cartId) {
        Cart cart = findCart(cartId);
        Product productDB = productService.saveProduct(product);
        mapCartProduct(cart, productDB);
        return product;
    }

    private void mapCartProduct(Cart cart, Product product){
        Optional<CartProductMap> findCartProductMap =
                Optional.ofNullable(cartProductMapRepository.findByCartAndProduct(cart, product));

        if(findCartProductMap.isPresent()){
            findCartProductMap.get().setQuantity(findCartProductMap.get().getQuantity()+1);
            cartProductMapRepository.save(findCartProductMap.get());
        }
        else{
            cartProductMapRepository
                    .save(
                            new CartProductMap(
                                    new CartProductMapId(cart.getCartId(),
                                    product.getProductId()),
                                    cart,
                                    product,1
                            )
                    );
        }
    }

    public Cart findCart(Long cartId){
       Optional<Cart> cart =  cartRepository.findById(cartId);

       if(cart.isPresent()){
           return cart.get();
       }else{
           throw new NotFoundException("Cart does not exist!");
       }
    }


    public String addProductToCart(SendMessageToCartDto productAndUserInfo){
        return "NOT IMPLEMENTED YET";
    }
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart findByOwner(Owner owner){
        return cartRepository.findByOwner(owner);
    }
}
