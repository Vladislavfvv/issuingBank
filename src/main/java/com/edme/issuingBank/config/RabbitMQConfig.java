package com.edme.issuingBank.config;

import com.edme.issuingBank.handler.TransactionMessageHandler;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {

    //    public static final String TRANSACTION_EXCHANGE = "transaction.exchange";
//    public static final String TRANSACTION_QUEUE = "transaction.queue";
//    public static final String TRANSACTION_ROUTING_KEY = "transaction.routing.key";
//
//    @Bean
//    public DirectExchange transactionExchange() {
//        return new DirectExchange(TRANSACTION_EXCHANGE);
//    }
//
//    @Bean
//    public Queue transactionQueue() {
//        return QueueBuilder.durable(TRANSACTION_QUEUE)
//                .withArgument("x-dead-letter-exchange", "dlx.exchange")
//                .withArgument("x-dead-letter-routing-key", "dlx.routing.key")
//                .build();
//    }
//
//    @Bean
//    public Binding transactionBinding() {
//        return BindingBuilder.bind(transactionQueue())
//                .to(transactionExchange())
//                .with(TRANSACTION_ROUTING_KEY);
//    }
//
//    // Контейнер, который слушает очередь и вызывает listenerAdapter
//    //listenerAdapter направляет сообщение в handleMessage
//    @Bean
//    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//                                                  MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(TRANSACTION_QUEUE);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//
//    // Адаптер связывает приходящее тело JSON → вызов метода handleMessage(Object)
//    //DTO сериализуется через Jackson2JsonMessageConverter
//    //Контроллер реализует API (но RabbitMQ работает вне этого), т.е. все вызывается автоматически
//    @Bean
//    public MessageListenerAdapter listenerAdapter(TransactionMessageHandler handler) {
//        return new MessageListenerAdapter(handler, "handleMessage");
//    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
} 