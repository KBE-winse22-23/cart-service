package com.onlineshop.cart.port.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String EXCHANGE = "onlineshop_message_exchange";

    public static final String CART_QUEUE = "cart_message_queue";
    public static final String WAREHOUSE_QUEUE = "warehouse_message_queue";
    public static final String PRODUCT_QUEUE = "product_message_queue";
    public static final String PRODUCT_UPDATE_QUEUE = "product_update_message_queue";

    public static final String CART_ROUTING_KEY = "cart_routing_key";
    public static final String WAREHOUSE_ROUTING_KEY = "warehouse_routing_key";
    public static final String PRODUCT_ROUTING_KEY = "product_routing_key";
    public static final String PRODUCT_UPDATE_ROUTING_KEY = "product_update_routing_key";


    @Bean
    public Queue queue(){
        return new Queue(CART_QUEUE);
    }

    @Bean
    public Queue queue2(){
        return new Queue(WAREHOUSE_QUEUE);
    }

    @Bean
    public Queue queue3(){
        return new Queue(PRODUCT_QUEUE);
    }


    @Bean
    public Queue queue4(){
        return new Queue(PRODUCT_UPDATE_QUEUE);
    }


    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }



    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(CART_ROUTING_KEY);
    }

    @Bean
    public Binding binding2(Queue queue2, TopicExchange exchange){
        return BindingBuilder
                .bind(queue2)
                .to(exchange)
                .with(WAREHOUSE_ROUTING_KEY);
    }

    @Bean
    public Binding binding3(Queue queue3, TopicExchange exchange){
        return BindingBuilder
                .bind(queue3)
                .to(exchange)
                .with(PRODUCT_ROUTING_KEY);
    }


    @Bean
    public Binding binding4(Queue queue4, TopicExchange exchange){
        return BindingBuilder
                .bind(queue4)
                .to(exchange)
                .with(PRODUCT_UPDATE_ROUTING_KEY);
    }



    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template =  new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
