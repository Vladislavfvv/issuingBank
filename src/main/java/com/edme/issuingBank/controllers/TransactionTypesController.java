package com.edme.issuingBank.controllers;

import com.edme.issuingBank.dto.TransactionTypeDto;
import com.edme.issuingBank.services.TransactionTypesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/transactionTypes")
public class TransactionTypesController {
    @Autowired
    private TransactionTypesService transactionTypesService;

    @GetMapping
    public ResponseEntity<List<TransactionTypeDto>> getAllTransactionTypes() {
        return ResponseEntity.ok(transactionTypesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionTypeById(@PathVariable("id") Long id) {
        return transactionTypesService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveTransactionType(@RequestBody @Valid TransactionTypeDto transactionTypeDto) {
        log.debug("Saving transaction type {}", transactionTypeDto);
        return ResponseEntity.ok(transactionTypesService.save(transactionTypeDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionTypeDto> updateTransactionType(@PathVariable Long id, @RequestBody @Valid TransactionTypeDto transactionTypeDto) {
        log.debug("Updating transaction type {}", transactionTypeDto);
        return transactionTypesService.update(id, transactionTypeDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/createTableTransactionTypes")
    public ResponseEntity<String> createTableTransactionTypes() {
        log.debug("Creating table transaction types");
        return transactionTypesService.createTable()
                ? ResponseEntity.ok("Creating table transaction types")
                : ResponseEntity.badRequest().body("Failed creating table transaction types");
    }

    @PostMapping("/fillTableTransactionTypes")
    public ResponseEntity<String> fillTableTransactionTypes() {
        log.debug("Filling table transaction types");
        return transactionTypesService.initializeTable()
                ? ResponseEntity.ok("Filling table transaction types")
                : ResponseEntity.badRequest().body("Failed filling table transaction types");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransactionType(@PathVariable Long id) {
        log.debug("Deleting transaction type {}", id);
        return transactionTypesService.delete(id)
                ? ResponseEntity.ok("Successfull deleting transaction type " + id)
                : ResponseEntity.badRequest().body("Failed deleting transaction type " + id);
    }

    @DeleteMapping("/clearTableTransactionTypes")
    public ResponseEntity<String> clearTableTransactionTypes() {
        log.debug("Clearing table transaction types");
        return transactionTypesService.deleteAll()
                ? ResponseEntity.ok("Successfully cleared table transaction types")
                : ResponseEntity.badRequest().body("Failed clearing table transaction types");
    }

    @DeleteMapping("/dropTableTransactionTypes")
    public ResponseEntity<String> dropTableTransactionTypes() {
        log.debug("Dropping table transaction types");
        return transactionTypesService.dropTable()
                ? ResponseEntity.ok("Successfully dropped table transaction types")
                : ResponseEntity.badRequest().body("Failed dropping table transaction types");
    }
}
