package com.fifi.bettingApp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "betting_app_exchange";
    public static final String USER_REGISTERED_QUEUE_NAME = "user.registered.queue";
    public static final String USER_REGISTERED_ROUTING_KEY = "user.registered";

    //exchange to skrzynka pocztowa
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    //queue z wiadomosciami
    @Bean
    public Queue userRegisteredQueue() {
        return new Queue(USER_REGISTERED_QUEUE_NAME);
    }

    //laczy skrzynke i kolejke przy uzyciu klucza binding
    //kazde wiad wyslane do skrzynki z kluczem user.registered maja trafic do kolejki
    @Bean
    public Binding bindingUserRegistered(Queue userRegisteredQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userRegisteredQueue).to(exchange).with(USER_REGISTERED_ROUTING_KEY);
    }

    //do zamainy z json lub do
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
