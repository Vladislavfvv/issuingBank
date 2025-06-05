package com.edme.issuingBank.controllers;

import com.edme.issuingBank.dto.BankSettingDto;
import com.edme.issuingBank.services.BankSettingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bankSettings")
public class BankSettingsController {

    @Autowired
    private BankSettingService bankSettingService;

    @GetMapping
    public ResponseEntity<List<BankSettingDto>> getListBankSettings() {
        return ResponseEntity.ok(bankSettingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankSettingDto> getBankSettingById(@PathVariable("id") Long id) {
        return bankSettingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveBankSetting(@RequestBody @Valid BankSettingDto bankSettingDto) {
        log.debug("Save Bank Setting: {}", bankSettingDto);
        return ResponseEntity.ok(bankSettingService.save(bankSettingDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBankSetting(@PathVariable Long id, @RequestBody BankSettingDto bankSettingDto) {
        log.debug("Update Bank Setting: {}", bankSettingDto);
        return bankSettingService.update(id, bankSettingDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBankSetting(@PathVariable Long id) {
        log.debug("Deleting bank setting {}", id);
        return bankSettingService.delete(id)
                ? ResponseEntity.ok("Successfully delete bankSetting")
                : ResponseEntity.badRequest().body("Failed deleting bankSetting");
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
