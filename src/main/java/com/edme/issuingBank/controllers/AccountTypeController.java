package com.edme.issuingBank.controllers;

import com.edme.issuingBank.dto.AccountTypeDto;
import com.edme.issuingBank.services.AccountTypeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/accountTypes")
public class AccountTypeController {

    @Autowired
    private AccountTypeService accountTypeService;

    @GetMapping
    public ResponseEntity<List<AccountTypeDto>> getAllAccountTypes() {
        return ResponseEntity.ok(accountTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountTypeDto> getAccountTypeById(@PathVariable("id") long id) {
        return accountTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveAccountType(@RequestBody @Valid AccountTypeDto accountTypeDto) {
        return ResponseEntity.ok(accountTypeService.save(accountTypeDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountTypeDto> updateAccountType(@PathVariable Long id, @RequestBody AccountTypeDto accountTypeDto) {
        return accountTypeService.update(id, accountTypeDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/createTableAccountTypes")
    public ResponseEntity<String> createTableAccountTypes() {
        log.debug("Creating table accountTypes");
        return accountTypeService.createTable()
                ? ResponseEntity.ok("Successfully created table accountTypes")
                : ResponseEntity.badRequest().body("Failed to create table accountTypes");
    }

    @PostMapping("/fillTableAccountTypes")
    public ResponseEntity<String> fillTableAccountTypes() {
        log.debug("Filling table accountTypes");
        return accountTypeService.initializeTable()
                ? ResponseEntity.ok("Successfully initialized table accountTypes")
                : ResponseEntity.status(500).body("Failed to initialize table accountTypes");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccountType(@PathVariable long id) {
        log.debug("Deleting account type {}", id);
        return accountTypeService.delete(id)
                ? ResponseEntity.ok("Successfully deleted accountType")
                : ResponseEntity.badRequest().body("Failed to delete accountType");
    }

    @DeleteMapping("/clearTableAccounts")
    public ResponseEntity<String> clearTableAccounts() {
        log.debug("Clearing table accounts");
        return accountTypeService.deleteAll()
                ? ResponseEntity.ok("Successfully cleared table accounts")
                : ResponseEntity.badRequest().body("Failed to clear table accounts");
    }

    @DeleteMapping("/dropTableAccounts")
    public ResponseEntity<String> dropTableAccounts() {
        log.debug("Dropping table accounts");
        return accountTypeService.dropTable()
                ? ResponseEntity.ok("Successfully dropped table accounts")
                : ResponseEntity.status(500).body("Failed to drop table accounts");
    }


}
