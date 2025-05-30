package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.TransactionTypeDto;
import com.edme.issuingBank.exceptions.ResourceNotFoundException;
import com.edme.issuingBank.mappers.TransactionTypeMapper;
import com.edme.issuingBank.models.TransactionType;
import com.edme.issuingBank.repositories.TransactionTypesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionTypesService implements AbstractService<Long, TransactionTypeDto> {
    private final TransactionTypesRepository ttRepository;
    private final TransactionTypeMapper ttMapper;

    public TransactionTypesService(TransactionTypesRepository transactionTypesRepository, TransactionTypeMapper transactionTypeMapper) {
        this.ttRepository = transactionTypesRepository;
        this.ttMapper = transactionTypeMapper;
    }

    @Override
    public List<TransactionTypeDto> findAll() {
        return ttRepository.findAll()
                .stream()
                .map(ttMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TransactionTypeDto> findById(Long aLong) {
        return ttRepository.findById(aLong)
                .map(ttMapper::toDto);
    }

    @Override
    @Transactional
    public Optional<TransactionTypeDto> save(TransactionTypeDto dto) {
        Optional<TransactionType> existing = ttRepository.findById(dto.getId());
        if (existing.isPresent()) {
            log.info("TransactionTypeDto {} already exists", dto.getId());
            return Optional.empty();
        }
        dto.setId(null);
        TransactionType saved = ttRepository.saveAndFlush(ttMapper.toEntity(dto));
        log.info("TransactionTypeDto {} saved", saved.getId());
        return Optional.of(ttMapper.toDto(saved));
    }

    @Override
    @Transactional
    public Optional<TransactionTypeDto> update(Long id, TransactionTypeDto dto) {
        Optional<TransactionType> existing = ttRepository.findById(id);
        if (existing.isEmpty()) {
            log.info("TransactionTypeDto {} does not exist", dto.getId());
            return Optional.empty();
        }
        TransactionType transactionType = existing.get();
        dto.setTransactionTypeName(transactionType.getTransactionTypeName());
        TransactionType updated = ttRepository.saveAndFlush(ttMapper.toEntity(dto));
        log.info("TransactionTypeDto {} updated", updated.getId());
        return Optional.of(ttMapper.toDto(updated));
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<TransactionType> existing = ttRepository.findById(id);
        if (existing.isEmpty()) {
            log.info("TransactionTypeDto {} does not exist", id);
            return false;
        }
        ttRepository.deleteById(id);
        log.info("TransactionTypeDto {} deleted", id);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteAll() {
        try {
            ttRepository.deleteAll();
            log.info("All transactionTypes {} deleted", ttRepository.findAll().size());
            return true;
        } catch (ResourceNotFoundException e) {
            log.info("TransactionTypes not found, cause {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public void dropTable() {
        try {
            ttRepository.dropTable();
            log.info("Table transactionTypes {} dropped", ttRepository.findAll().size());
        } catch (ResourceNotFoundException e) {
            log.info("Table transactionTypes not found, cause {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void createTable() {
        try {
            ttRepository.createTable();
            log.info("Table transactionTypes created");
        } catch (ResourceNotFoundException e) {
            log.info("Table transactionTypes not created, cause {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void initializeTable() {
        try {
            ttRepository.insertDefaultValues();
            log.info("Table transactionTypes initialized");
        } catch (ResourceNotFoundException e) {
            log.info("Table transactionTypes not initialized, cause {}", e.getMessage());
        }
    }
}
