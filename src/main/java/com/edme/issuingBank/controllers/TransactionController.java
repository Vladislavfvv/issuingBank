package com.edme.issuingBank.controllers;

import com.edme.issuingBank.dto.TransactionDto;
import com.edme.issuingBank.services.PaymentSystemService;
import com.edme.issuingBank.services.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private PaymentSystemService paymentSystemService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id") Long id) {
        return transactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        log.debug("Saving transaction: {}", transactionDto);
        return ResponseEntity.ok(transactionService.save(transactionDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id, @RequestBody @Valid TransactionDto transactionDto) {
        log.debug("Updating transaction with id {}", id);
        return transactionService.update(id, transactionDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/createTableTransactions")
    public ResponseEntity<String> createTableTransactions() {
        log.debug("Creating table transactions");
        return transactionService.createTable()
                ? ResponseEntity.ok("Creating table transactions successfully")
                : ResponseEntity.badRequest().body("Creating table transactions failed");
    }

    @PostMapping("/fillTableTransactions")
    public ResponseEntity<String> fillTableTransactions() {
        log.debug("Filling table transactions");
        return transactionService.initializeTable()
                ? ResponseEntity.ok("Filling table transactions successfully")
                : ResponseEntity.badRequest().body("Filling table transactions failed");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        log.debug("Deleting transaction with id {}", id);
        return transactionService.delete(id)
                ? ResponseEntity.ok("Deleting transaction successfully")
                : ResponseEntity.badRequest().body("Deleting transaction failed");
    }

    @DeleteMapping("/clearTableTransactions")
    public ResponseEntity<String> clearTableTransactions() {
        log.debug("Clearing table transactions");
        return transactionService.deleteAll()
                ? ResponseEntity.ok("Clearing table transactions successfully")
                : ResponseEntity.badRequest().body("Clearing table transactions failed");
    }

    @DeleteMapping("/dropTableTransactions")
    public ResponseEntity<String> dropTableTransactions() {
        log.debug("Dropping table transactions");
        return transactionService.dropTable()
                ? ResponseEntity.ok("Dropping table transactions successfully")
                : ResponseEntity.badRequest().body("Dropping table transactions failed");
    }
}
