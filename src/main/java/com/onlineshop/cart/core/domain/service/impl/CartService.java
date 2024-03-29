package com.onlineshop.cart.core.domain.service.impl;


import com.onlineshop.cart.core.domain.dto.CartProductMapDto;
import com.onlineshop.cart.core.domain.dto.ProductDto;
import com.onlineshop.cart.core.domain.dto.SendMessageToCartDto;
import com.onlineshop.cart.core.domain.model.*;
import com.onlineshop.cart.core.domain.service.interfaces.CartProductMapRepository;
import com.onlineshop.cart.core.domain.service.interfaces.CartRepository;
import com.onlineshop.cart.core.domain.service.interfaces.OwnerRepository;
import com.onlineshop.cart.port.user.exception.EmptyFieldException;
import com.onlineshop.cart.port.user.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private OwnerRepository ownerRepository;

    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    public Cart createCartForUser(Owner owner) throws EmptyFieldException {
        if(owner==null){
            throw new EmptyFieldException("Owner is empty. Can't create a shopping cart");
        }
        if(owner.getEmail()==null || owner.getEmail().length()<=0){
            throw new EmptyFieldException("Owner id is empty. Can't create a shopping cart");
        }else if(owner.getFirstName().equals("")){
            throw new EmptyFieldException("Owner first name is empty. Can't create a shopping cart");
        }else if(owner.getLastName().equals("")){
            throw new EmptyFieldException("Owner last name is empty. Can't create a shopping cart");
        }


        Optional<Owner> checkIfCartAlreadyExists = ownerRepository.findByEmail(owner.getEmail());
        if(checkIfCartAlreadyExists.isPresent()){
            throw new IllegalArgumentException("Cart for the given Owner already exists!");
        }
        Cart cart = new Cart();
        cart.setOwner(owner);
        cartRepository.save(cart);
        return cart;
    }



    public Product addProductToAnExistingCart(Product product, Long cartId) throws NotFoundException {
        Cart cart = findCart(cartId);
        Product productDB = productService.saveProduct(product);
        mapCartProduct(cart, productDB);
        return product;
    }

    private void mapCartProduct(Cart cart, Product product){
        Optional<CartProductMap> findCartProductMap =
                cartProductMapRepository.findByCartAndProduct(cart, product);

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

    public Cart findCart(Long cartId) throws com.onlineshop.cart.port.user.exception.NotFoundException {
       Optional<Cart> cart =  cartRepository.findById(cartId);

       if(cart.isPresent()){
           return cart.get();
       }else{
           throw new com.onlineshop.cart.port.user.exception.NotFoundException("Cart with given id " + cartId + " not found!");
       }
    }


    public String createCartAndAddProductConsumer(SendMessageToCartDto productAndUserInfo) throws NotFoundException, EmptyFieldException {

        Owner owner =
                new Owner(null,
                        productAndUserInfo.getOwnerFirstName(),
                        productAndUserInfo.getOwnerLastName(),
                        productAndUserInfo.getEmail());

        Product product = new Product();
        product.setProductId(productAndUserInfo.getProductId());
        product.setProductName(productAndUserInfo.getProductName());
        product.setProductPrice(productAndUserInfo.getProductPrice());
        product.setImage(productAndUserInfo.getImage());


        Optional<Cart> cartDB = cartRepository.findByOwnerEmail(owner.getEmail());
        if(cartDB.isEmpty()){
            Cart newCart = createCartForUser(owner);
            addProductToAnExistingCart(product, newCart.getCartId());
        }else{
            addProductToAnExistingCart(product, cartDB.get().getCartId());
        }


        return "Product added to cart!";
    }

    public Cart findByOwner(Owner owner) throws NotFoundException {
        Optional<Cart> cart = cartRepository.findByOwner(owner);
        if(cart.isEmpty()){
            throw new NotFoundException("Cart with the given owner was not found!");
        }
        return cart.get();
    }

    public List<ProductDto> getProductsFromCart(Long cartId) throws com.onlineshop.cart.port.user.exception.NotFoundException {
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
            throw new com.onlineshop.cart.port.user.exception.NotFoundException("Shopping cart not found!");
        }

        return products;
    }

    public int countProductsInCart(Long cartId) throws com.onlineshop.cart.port.user.exception.NotFoundException {
        return getProductsFromCart(cartId).size();
    }

    public boolean removeProductFromCart(CartProductMapDto cartProductMapDto) throws NotFoundException {
        CartProductMap cartProductMap = getCartProductMap(cartProductMapDto);
        cartProductMapRepository.delete(cartProductMap);
        return true;
    }

    public CartProductMap getCartProductMap(CartProductMapDto cartProductMapDto) throws NotFoundException {
        Optional<Cart> cart = cartRepository.findById(cartProductMapDto.getCartId());
        if(cart.isEmpty()){
            throw new NotFoundException("Shopping cart not found!");
        }
        Product product = productService.getProduct(cartProductMapDto.getProductId());
        Optional<CartProductMap> cartProductMap = cartProductMapRepository.findByCartAndProduct(cart.get(), product);
        if(cartProductMap.isEmpty()){
            throw new NotFoundException("Cart Product map not found!");
        }
        return cartProductMap.get();
    }

    public CartProductMap increaseProductQuantity(CartProductMapDto cartProductMapDto) throws NotFoundException {
        CartProductMap cartProductMap = getCartProductMap(cartProductMapDto);
        cartProductMap.setQuantity(cartProductMap.getQuantity()+1);
        return cartProductMapRepository.save(cartProductMap);
    }

    public double cartSubtotal(Long cartId) throws NotFoundException {
        final double[] subtotal = {0};
        List<ProductDto> products = getProductsFromCart(cartId);

        products.forEach(product -> subtotal[0] = subtotal[0] + product.getProductPrice()*product.getProductQuantity());

        return subtotal[0];
    }

    public Cart findByOwnerEmail(String email) throws NotFoundException {
        Optional<Cart> cart = cartRepository.findByOwnerEmail(email);

        if(cart.isEmpty()){
            throw new NotFoundException("Cart with given Email " + email +" not found!");
        }
        return cart.get();
    }

    public CartProductMap decreaseProductQuantity(CartProductMapDto cartProductMapDto) throws NotFoundException {
        CartProductMap cartProductMap = getCartProductMap(cartProductMapDto);
        if(cartProductMap.getQuantity()>1){
            cartProductMap.setQuantity(cartProductMap.getQuantity()-1);
            return cartProductMapRepository.save(cartProductMap);
        }
        throw new IllegalArgumentException("Have reached the minimum quantity 1. Can't decrease quantity anymore!");

    }


    public boolean removeProducts(Long cartId) throws NotFoundException {
        Cart cart = findCart(cartId);
        List<CartProductMap> cartProductMap = cartProductMapRepository.findByCart(cart);
        cartProductMap.forEach(item -> cartProductMapRepository.delete(item));
        return true;
    }
}
