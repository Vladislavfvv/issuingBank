package com.edme.issuingBank.handler;

import com.edme.issuingBank.dto.TransactionExchangeIbDto;
import com.edme.issuingBank.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionMessageHandler {

    private final TransactionService transactionService;

    public TransactionMessageHandler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

//    public void handleMessage(Object message) {
//        try {
//            log.info("Received transaction message: {}", message);
//            transactionService.processTransaction(message);
//        } catch (Exception e) {
//            log.error("Error processing transaction message: {}", e.getMessage(), e);
//            throw e;
//        }
//    }

    public void handleMessage(TransactionExchangeIbDto dto) {
        try {
            log.info("Received transaction message: {}", dto);
            transactionService.processTransaction(dto);
        } catch (Exception e) {
            log.error("Error processing transaction message: {}", e.getMessage(), e);
            throw e;
        }
    }
} 