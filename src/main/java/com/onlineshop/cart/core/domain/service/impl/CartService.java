package com.onlineshop.cart.core.domain.service.impl;


import com.onlineshop.cart.core.domain.dto.CartProductMapDto;
import com.onlineshop.cart.core.domain.dto.ProductDto;
import com.onlineshop.cart.core.domain.dto.SendMessageToCartDto;
import com.onlineshop.cart.core.domain.model.*;
import com.onlineshop.cart.core.domain.service.interfaces.CartProductMapRepository;
import com.onlineshop.cart.core.domain.service.interfaces.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Owner owner =
                new Owner(productAndUserInfo.getOwnerId(),
                        productAndUserInfo.getOwnerFirstName(),
                        productAndUserInfo.getOwnerLastName());

        Product product = new Product();
        product.setProductId(productAndUserInfo.getProductId());
        product.setProductName(productAndUserInfo.getProductName());
        product.setProductPrice(productAndUserInfo.getProductPrice());
        product.setImage(productAndUserInfo.getImage());


        Cart cartDB = cartRepository.findByOwner(owner);

        addProductToCart(product, cartDB.getCartId());

        return "Product added to cart!";
    }
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart findByOwner(Owner owner){
        Cart cart = cartRepository.findByOwner(owner);

         return cartRepository.findByOwner(owner);
    }

    public List<ProductDto> getProductsFromCart(Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        List<ProductDto> products = new ArrayList<>();

        if(cart.isPresent()){
            products = cart.get().getCartProductMaps().stream().map(item ->  new ProductDto(
                    item.getProduct().getProductId(),
                    item.getProduct().getProductName(),
                    item.getProduct().getProductPrice(),
                    item.getProduct().getImage(),
                    item.getQuantity()

            )).collect(Collectors.toList());
        }else{
            throw new NotFoundException("Shopping cart not found!");
        }

        return products;
    }

    public int countProductsInCart(Long cartId) {
        return getProductsFromCart(cartId).size();
    }

    public boolean removeProductFromCart(CartProductMapDto cartProductMapDto) {
        Optional<Cart> cart = cartRepository.findById(cartProductMapDto.getCartId());
        if(cart.isEmpty()){
            throw new NotFoundException("Shopping cart not found!");
        }
        Product product = productService.getProduct(cartProductMapDto.getProductId());

        CartProductMap cartProductMap = cartProductMapRepository.findByCartAndProduct(cart.get(), product);
        System.out.println("---------------------------------------------------------------------");

        cartProductMapRepository.delete(cartProductMap);

        return true;
    }
}
