package com.edme.issuingBank.controllers;

import com.edme.issuingBank.dto.BankSettingDto;
import com.edme.issuingBank.services.BankSettingService;
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
@Validated
@Tag(name = "Bank Settings Management", description = "APIs for managing bank settings")
@RequestMapping("/api/bankSettings")
public class BankSettingsController {
    private final BankSettingService bankSettingService;

    @Operation(summary = "Get all bank settings", description = "Retrieves a list of all bank settings")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all settings")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BankSettingDto>> getListBankSettings() {
        log.info("Fetching all bank settings");
        return ResponseEntity.ok(bankSettingService.findAll());
    }

    @Operation(summary = "Get bank setting by ID", description = "Retrieves a specific bank setting by its ID")
    @ApiResponse(responseCode = "200", description = "Setting found")
    @ApiResponse(responseCode = "404", description = "Setting not found")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BankSettingDto> getBankSettingById(
            @Parameter(description = "Setting ID", required = true) 
            @PathVariable("id") Long id) {
        log.info("Fetching bank setting with id: {}", id);
        return bankSettingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Create new bank setting", description = "Creates a new bank setting")
    @ApiResponse(responseCode = "201", description = "Setting created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BankSettingDto> saveBankSetting(
            @Valid @RequestBody BankSettingDto bankSettingDto) {
        log.info("Creating new bank setting: {}", bankSettingDto);
       Optional<BankSettingDto> savedSetting = bankSettingService.save(bankSettingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSetting.get());
    }

    @Operation(summary = "Update bank setting", description = "Updates an existing bank setting")
    @ApiResponse(responseCode = "200", description = "Setting updated successfully")
    @ApiResponse(responseCode = "404", description = "Setting not found")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BankSettingDto> updateBankSetting(
            @Parameter(description = "Setting ID", required = true) 
            @PathVariable Long id,
            @Valid @RequestBody BankSettingDto bankSettingDto) {
        log.info("Updating bank setting with id {}: {}", id, bankSettingDto);
        return bankSettingService.update(id, bankSettingDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Delete bank setting", description = "Deletes a bank setting by ID")
    @ApiResponse(responseCode = "204", description = "Setting deleted successfully")
    @ApiResponse(responseCode = "404", description = "Setting not found")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBankSetting(
            @Parameter(description = "Setting ID", required = true) 
            @PathVariable Long id) {
        log.info("Deleting bank setting with id: {}", id);
        if (bankSettingService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/createTableBankSettings")
    public ResponseEntity<?> createTableBankSettings() {
        log.debug("Creating table bank settings");
        return bankSettingService.createTable()
                ? ResponseEntity.ok("Creating table bankSettings")
                : ResponseEntity.badRequest().body("Creating table bankSettings failed");
    }

    @PostMapping("fillTableBankSettings")
    public ResponseEntity<?> fillTableBankSettings() {
        log.debug("Filling table bank settings");
        return bankSettingService.initializeTable()
                ? ResponseEntity.ok("Filling table bank settings")
                : ResponseEntity.badRequest().body("Filling table bank settings failed");
    }

    @DeleteMapping("/clearTableBankSettings")
    public ResponseEntity<String> clearTableBankSettings() {
        log.debug("Clearing table bank settings");
        return bankSettingService.deleteAll()
                ? ResponseEntity.ok("Successfully clear table bankSettings")
                : ResponseEntity.badRequest().body("Failed to clear table bankSettings");
    }

    @DeleteMapping("/dropTableBankSettings")
    public ResponseEntity<String> dropTableBankSettings() {
        log.debug("Dropping table bank settings");
        return bankSettingService.dropTable()
                ? ResponseEntity.ok("Successfully drop table bank settings")
                : ResponseEntity.badRequest().body("Failed to drop table bank settings");
    }

}
