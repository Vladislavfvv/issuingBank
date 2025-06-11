package com.edme.issuingBank.controllers;

import com.edme.issuingBank.dto.AccountDto;
import com.edme.issuingBank.mappers.AccountMapper;
import com.edme.issuingBank.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
//@Validated
//@Tag(name = "Account Management", description = "APIs for managing bank accounts")
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;


    @Operation(summary = "Get all accounts", description = "Retrieves a list of all bank accounts")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all accounts")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        log.info("Fetching all accounts");
        return ResponseEntity.ok(accountService.findAll());
    }

    @Operation(summary = "Get account by ID", description = "Retrieves a specific account by its ID")
    @ApiResponse(responseCode = "200", description = "Account found")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AccountDto> getAccountById(
            @Parameter(description = "Account ID", required = true)
            @PathVariable("id") long id) {
        log.info("Fetching account with id: {}", id);
        return accountService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Create new account", description = "Creates a new bank account")
    @ApiResponse(responseCode = "201", description = "Account created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDto> saveAccount(
            @Valid @RequestBody AccountDto accountDto) {
        log.info("Creating new account: {}", accountDto);
       Optional<AccountDto> savedAccount = accountService.save(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount.get());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateAccount(@PathVariable("id") long id, @RequestBody @Valid AccountDto accountDto) {
        return accountService.update(id, accountDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/createTableAccounts")
    public ResponseEntity<?> createTableAccounts(@RequestBody @Valid List<AccountDto> accountDtos) {
        log.info("Creating table accounts");
        if (accountService.createTable()) {
            return ResponseEntity.ok("Successfully created table accounts");
        } else {
            return ResponseEntity.badRequest().body("Failed to create table accounts");
        }
    }

    @PostMapping("/fillTableAccounts")
    public ResponseEntity<String> fillTableAccounts() {
        log.info("Filling table accounts");

        if (accountService.initializeTable()) {
            return ResponseEntity.ok("Successfully initialized table Accounts");
        } else {
            return ResponseEntity.status(500).body("Failed to initialize table Accounts");
        }
    }

    @Operation(summary = "Delete account", description = "Deletes an account by ID")
    @ApiResponse(responseCode = "204", description = "Account deleted successfully")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAccount(
            @Parameter(description = "Account ID", required = true)
            @PathVariable Long id) {
        log.info("Deleting account with id: {}", id);
        if (accountService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/clearTableAccounts")
    public ResponseEntity<String> clearTableAccounts() {
        log.info("Clearing table accounts");
        if (accountService.initializeTable()) {
            return ResponseEntity.ok("Successfully cleared table Accounts");
        }
        return ResponseEntity.status(500).body("Failed to clear table Accounts");
    }

    @DeleteMapping("/dropTableAccess")
    public ResponseEntity<String> dropTableAccess() {
        log.info("Dropping table access");
        if (accountService.initializeTable()) {
            return ResponseEntity.ok("Successfully dropped table access");
        }
        return ResponseEntity.status(500).body("Failed to drop table access");
    }
}

