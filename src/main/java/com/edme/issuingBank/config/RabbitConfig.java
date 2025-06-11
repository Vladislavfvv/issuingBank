package com.edme.issuingBank.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // public static final String TEST_QUEUE = "test.queue";

//    @Bean
//    public Queue testQueue() {
//        return new Queue(TEST_QUEUE, true);
//    }
}
