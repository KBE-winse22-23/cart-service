package com.onlineshop.cart.port.user.consumer;

import com.onlineshop.cart.core.domain.dto.SendMessageToCartDto;
import com.onlineshop.cart.core.domain.service.impl.CartService;
import com.onlineshop.cart.port.config.MQConfig;
import com.onlineshop.cart.port.user.exception.NotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartConsumer {

    @Autowired
    private CartService cartService;

    @RabbitListener(queues = MQConfig.CART_QUEUE)
    public void addProductToCart(SendMessageToCartDto productAndUserInfo) throws NotFoundException {
        System.out.println(productAndUserInfo);

        cartService.addProductToCart(productAndUserInfo);
    }
}
