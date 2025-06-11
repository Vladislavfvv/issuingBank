package com.edme.issuingBank.controllers;

import com.edme.issuingBank.dto.UserAccessDto;
import com.edme.issuingBank.services.UserAccessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userAccess")
public class UserAccessController {

    private final UserAccessService userAccessService;

    @GetMapping
    public ResponseEntity<List<UserAccessDto>> getAllUserAccesses() {
        return ResponseEntity.ok(userAccessService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccessDto> getUserAccessById(@PathVariable("id") Long id) {
        return userAccessService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveUserAccess(@RequestBody @Valid UserAccessDto userAccessDto) {
        log.debug("Saving user access: {}", userAccessDto);
        return ResponseEntity.ok(userAccessService.save(userAccessDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAccessDto> updateUserAccess(@PathVariable Long id, @RequestBody @Valid UserAccessDto userAccessDto) {
        log.debug("Updating user access with id {}", id);
        return userAccessService.update(id, userAccessDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/createTableUserAccess")
    public ResponseEntity<String> createTableUserAccess() {
        log.debug("Creating table user access");
        if (userAccessService.createTable()) {
            return ResponseEntity.ok("Successfully created table UserAccess");
        } else {
            return ResponseEntity.badRequest().body("Failed to create table UserAccess");
        }
    }

    @PostMapping("/fillTableUserAccess")
    public ResponseEntity<String> fillTableUserAccess() {
        log.debug("Filling table UserAccess");
//        if(!userAccessService.createTable()){
//            createTableUserAccess();
//        }
        if (userAccessService.initializeTable()) {
            return ResponseEntity.ok("Successfully initialized table UserAccess");
        } else {
            return ResponseEntity.status(500).body("Failed to initialize table UserAccess");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserAccess(@PathVariable Long id) {
        log.debug("Deleting user access");
        return userAccessService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/clearTableUserAccess")
    public ResponseEntity<String> clearTableUserAccess() {
        log.debug("Clearing table user access");
        if (userAccessService.deleteAll()){
            return ResponseEntity.ok("Successfully cleared table UserAccess");
        }else {
            return ResponseEntity.status(500).body("Failed to clear table UserAccess");
        }
    }

    @DeleteMapping("/dropTableUserAccess")
    public ResponseEntity<String> dropTableUserAccess() {
        log.info("Dropping table UserAccess");
        if (userAccessService.dropTable()){
            return ResponseEntity.ok("Successfully dropped table UserAccess");
        }
        else {
            return ResponseEntity.status(500).body("Failed to dropped table UserAccess");
        }
    }
}
