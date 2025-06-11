package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.TransactionDto;
import com.edme.issuingBank.dto.TransactionExchangeIbDto;
import com.edme.issuingBank.exceptions.ResourceNotFoundException;
import com.edme.issuingBank.mappers.AccountMapper;
import com.edme.issuingBank.mappers.TransactionExchangeIbMapper;
import com.edme.issuingBank.mappers.TransactionMapper;
import com.edme.issuingBank.mappers.TransactionTypeMapper;
import com.edme.issuingBank.models.Transaction;
import com.edme.issuingBank.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService implements AbstractService<Long, TransactionDto> {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final TransactionTypeMapper transactionTypeMapper;
    private final AccountMapper accountMapper;
    private final TransactionExchangeIbMapper exchangeIbMapper;


    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionDto> findById(Long aLong) {
        return transactionRepository.findById(aLong)
                .map(transactionMapper::toDto);
    }

    @Override
    public Optional<TransactionDto> save(TransactionDto dto) {
        Optional<Transaction> exist = transactionRepository.findById(dto.getId());
        if (exist.isPresent()) {
            log.info("Transaction already exists with id: {}", dto.getId());
            return Optional.empty();
        }
        dto.setId(null);
        Transaction saved = transactionRepository.saveAndFlush(transactionMapper.toEntity(dto));
        log.info("Saved transaction: {}", saved);
        return Optional.ofNullable(transactionMapper.toDto(saved));
    }

//    public void processTransaction(TransactionExchangeIbDto dto) {
//        Transaction transaction = exchangeIbMapper.toEntity(dto);
//        transactionRepository.save(transaction);
//    }

    public Transaction processTransaction(TransactionExchangeIbDto dto) {
        Transaction transaction = exchangeIbMapper.toEntity(dto);
        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<TransactionDto> update(Long id, TransactionDto dto) {
        Optional<Transaction> exist = transactionRepository.findById(dto.getId());
        if (exist.isEmpty()) {
            log.info("Transaction does not exist with id: {}", dto.getId());
            return Optional.empty();
        }
        Transaction saved = transactionRepository.saveAndFlush(transactionMapper.toEntity(dto));
        log.info("Updated transaction: {}", saved);
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        Optional<Transaction> exist = transactionRepository.findById(id);
        if (exist.isPresent()) {
            transactionRepository.delete(exist.get());
            log.info("Transaction deleted: {}", id);
            return true;
        }
        log.info("Transaction not deleted with id: {}", id);
        return false;
    }

    @Override
    public boolean deleteAll() {
        try {
            transactionRepository.deleteAll();
            log.info("Transaction deleted all");
            return true;
        } catch (EntityNotFoundException e) {
            log.info("Transaction not deleted, cause: {} ", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean dropTable() {
        try {
            transactionRepository.dropTable();
            log.info("Transaction table dropped");
        } catch (EntityNotFoundException e) {
            log.info("Transaction not dropped, cause: {} ", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean createTable() {
        try {
            transactionRepository.createTable();
            log.info("Transaction table created");
        } catch (ResourceNotFoundException e) {
            log.info("Transaction not created, cause: {} ", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean initializeTable() {
        try {
            transactionRepository.insertDefaultValues();
            log.info("Transaction table initialized");
        } catch (ResourceNotFoundException e) {
            log.info("Transaction not inserted, cause: {} ", e.getMessage());
        }
        return false;
    }
}
