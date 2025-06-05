package com.edme.issuingBank.controllers;

import com.edme.issuingBank.dto.ClientDto;
import com.edme.issuingBank.services.ClientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("id") Long id) {
        return clientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveClient(@RequestBody @Valid ClientDto clientDto) {
        log.info("Saving client {}", clientDto);
        return ResponseEntity.ok(clientService.save(clientDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        log.info("Updating client {}", clientDto);
        return clientService.update(id, clientDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/createTableClients")
    public ResponseEntity<String> createTableClients() {
        log.debug("Creating table clients");
        return clientService.createTable()
                ?  ResponseEntity.ok("Creating table clients")
                : ResponseEntity.badRequest().body("Creating table clients failed");
    }

    @PostMapping("/fillTableClients")
    public ResponseEntity<String> fillTableClients() {
        log.debug("Filling table clients");
        return clientService.initializeTable()
                ? ResponseEntity.ok("Successfull filling table clients")
                : ResponseEntity.badRequest().body("Filling table clients failed");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        log.info("Deleting client {}", id);
        return clientService.delete(id)
                ? ResponseEntity.ok("Deleting client " + id)
                : ResponseEntity.badRequest().body("Deleting client failed");
    }

    @DeleteMapping("/clearTableClients")
    public ResponseEntity<String> clearTableClients() {
        log.debug("Clearing table clients");
        return clientService.deleteAll()
                ? ResponseEntity.ok("Clearing table clients")
                : ResponseEntity.badRequest().body("Clearing table clients failed");
    }

    @DeleteMapping("/dropTableClients")
    public ResponseEntity<String> dropTableClients() {
        log.debug("Dropping table clients");
        return clientService.dropTable()
                ? ResponseEntity.ok("Dropping table clients")
                : ResponseEntity.badRequest().body("Dropping table clients failed");
    }
}
