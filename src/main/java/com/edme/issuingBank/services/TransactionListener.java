package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.MyDto;
import com.edme.issuingBank.dto.TransactionExchangeIbDto;
import com.edme.issuingBank.models.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionListener {

    @Value("${callback.queue}")
    private String callbackQueue;

    private final TransactionService transactionService;
    private final AmqpTemplate amqpTemplate;
    private final Tracer tracer;

//    @RabbitListener(queues = "main")
//    public void receive(TransactionExchangeIbDto dto) {
//        System.out.println(">>> Получено сообщение из test.queue:");
//        System.out.println("id: " + dto.getId());
//        System.out.println("transactionDate: " + dto.getTransactionDate());
//        System.out.println("sum: " + dto.getSum());
//        System.out.println("transactionName: " + dto.getTransactionName());
//        System.out.println("transactionType: " + dto.getTransactionType());
//        System.out.println("account: " + dto.getAccount());
//        System.out.println("sentToProcessingCenter: " + dto.getSentToProcessingCenter());
//        System.out.println("receivedFromProcessingCenter: " + dto.getReceivedFromProcessingCenter());
//
//        // Поменяем имя и отправляем обратно
//        TransactionExchangeIbDto response = new TransactionExchangeIbDto();
//        response.setId(dto.getId());
//        response.setTransactionDate(dto.getTransactionDate());
//        response.setSum(dto.getSum());
//        response.setTransactionName(dto.getTransactionName() + "Hello from IBank");
//        response.setTransactionType(dto.getTransactionType());
//        response.setAccount(dto.getAccount());
//        response.setSentToProcessingCenter(OffsetDateTime.now());
//

    /// /        response.setName(dto.getName() + "_Processed AND Changed");
    /// /        response.setCreatedAt(LocalDateTime.now());
//
//        // Отправка обратно Producer'у
//        amqpTemplate.convertAndSend(callbackQueue, response);
//        log.info("<<< Ответ отправлен обратно в очередь '{}'", callbackQueue);
//    }
//    @RabbitListener(queues = "main")
//    public void receive(TransactionExchangeIbDto dto) {
//        log.info(">>> Получено сообщение из очереди:");
//        log.info("id: {}", dto.getId());
//        log.info("transactionName: {}", dto.getTransactionName());
//
//        // Сохраняем в базу
//        Transaction savedTransaction = transactionService.processTransaction(dto);
//        log.info(">>> Транзакция сохранена в базе с id = {}", savedTransaction.getId());
//
//        // Формируем ответ с обновлённым id
//        TransactionExchangeIbDto response = new TransactionExchangeIbDto();
//        response.setId(savedTransaction.getId()); //  тут  возвращаем настоящий id
//        response.setTransactionDate(savedTransaction.getTransactionDate());
//        response.setSum(savedTransaction.getSum());
//        response.setTransactionName(savedTransaction.getTransactionName() + " Hello from IBank");
//        response.setTransactionType(savedTransaction.getTransactionType().getId());
//        response.setAccount(savedTransaction.getAccount().getId());
//        response.setSentToProcessingCenter(OffsetDateTime.now());
//
//        // Отправка обратно в callback очередь
//        amqpTemplate.convertAndSend(callbackQueue, response);
//        log.info("<<< Ответ отправлен обратно в очередь '{}'", callbackQueue);
//    }


    @RabbitListener(queues = "main")
    public void receive(TransactionExchangeIbDto dto) {
        // Создаём span для получения сообщения
        Span span = tracer.spanBuilder("RabbitMQ.receiveTransaction").startSpan();

        try (Scope scope = span.makeCurrent()) {
            span.setAttribute("rabbitmq.message.id", String.valueOf(dto.getId()));
            span.setAttribute("rabbitmq.transaction.name", dto.getTransactionName());

            log.info(">>> Получено сообщение из очереди:");
            log.info("id: {}", dto.getId());
            log.info("transactionName: {}", dto.getTransactionName());

            // Сохраняем в базу
            Transaction savedTransaction = transactionService.processTransaction(dto);
            log.info(">>> Транзакция сохранена в базе с id = {}", savedTransaction.getId());
            span.setAttribute("transaction.saved.id", savedTransaction.getId());

            // Формируем ответ с обновлённым id
            TransactionExchangeIbDto response = new TransactionExchangeIbDto();
            response.setId(savedTransaction.getId());
            response.setTransactionDate(savedTransaction.getTransactionDate());
            response.setSum(savedTransaction.getSum());
            response.setTransactionName(savedTransaction.getTransactionName() + " Hello from IBank");
            response.setTransactionType(savedTransaction.getTransactionType().getId());
            response.setAccount(savedTransaction.getAccount().getId());
            response.setSentToProcessingCenter(OffsetDateTime.now());

            // Отправка обратно в callback очередь
            amqpTemplate.convertAndSend(callbackQueue, response);
            span.addEvent("Callback message sent");
            log.info("<<< Ответ отправлен обратно в очередь '{}'", callbackQueue);

            span.setStatus(io.opentelemetry.api.trace.StatusCode.OK);
        } catch (Exception e) {
            span.recordException(e);
            span.setStatus(io.opentelemetry.api.trace.StatusCode.ERROR, "Exception while processing RabbitMQ message");
            throw e;
        } finally {
            span.end();
        }
    }
}
