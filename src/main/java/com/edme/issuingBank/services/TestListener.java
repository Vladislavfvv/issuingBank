package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.MyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


//@Service
//@Slf4j
//@RequiredArgsConstructor
public class TestListener {

//    @Value("${callback.queue}")
//    private String callbackQueue;
//
//    private final AmqpTemplate amqpTemplate;
//
//    @RabbitListener(queues = "Second")
//    public void receive(MyDto dto) {
//        System.out.println(">>> Получено сообщение из test.queue:");
//        System.out.println("Name: " + dto.getName());
//        System.out.println("CreatedAt: " + dto.getCreatedAt());
//
//        // Пример простой логики: меняем имя и отправляем обратно
//        MyDto response = new MyDto();
//        response.setName(dto.getName() + "_Processed AND Changed");
//        response.setCreatedAt(LocalDateTime.now());
//
//        // Отправка обратно Producer'у
//        amqpTemplate.convertAndSend(callbackQueue, response);
//        log.info("<<< Ответ отправлен обратно в очередь '{}'", callbackQueue);
//    }

}
