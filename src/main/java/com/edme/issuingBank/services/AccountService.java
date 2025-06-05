package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.*;
import com.edme.issuingBank.exceptions.ResourceNotFoundException;
import com.edme.issuingBank.mappers.*;
import com.edme.issuingBank.models.*;
import com.edme.issuingBank.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService implements AbstractService<Long, AccountDto> {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ClientService clientService;
    private final CurrencyService currencyService;
    private final AccountTypeService accountTypeService;
    private final AccountTypeMapper accountTypeMapper;
    private final ClientMapper clientMapper;
    private final CurrencyMapper currencyMapper;


    // Кеширует список всех банков
    @Cacheable(value = "allAccountsCache")
    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "accountByIdCache", key = "#id")
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountDto> findById(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::toDto);
    }

    @Override
    //удаление кэша при сохранении нового
    @CacheEvict(value = {"allAccountsCache", "accountByIdCache"}, allEntries = true)
    public Optional<AccountDto> save(AccountDto dto) {
        Optional<Account> existing = accountRepository.findByAccountNumber(dto.getAccountNumber());
        if (existing.isPresent()) {
            log.info("Account already exists with account number: {}", dto.getAccountNumber());
            return Optional.empty();
        }
        dto.setId(null);
        Account saved = accountRepository.saveAndFlush(accountMapper.toEntity(dto));
        log.info("Saved account id: {}", saved.getId());
        return Optional.ofNullable(accountMapper.toDto(saved));
    }


    private void updateEntityFromDto(Account account, AccountDto dto) {
        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(dto.getBalance());
        account.setAccountType(accountTypeService.findById(dto.getAccountType().getId()).map(accountTypeMapper::toEntity).orElseThrow());
        account.setClient(clientService.findById(dto.getClient().getId()).map(clientMapper::toEntity).orElseThrow());
        account.setCurrency(currencyService.findById(dto.getCurrency().getId()).map(currencyMapper::toEntity).orElseThrow());
        account.setAccountOpeningDate(Date.valueOf(dto.getAccountOpeningDate()));
        account.setSuspendingOperations(dto.isSuspendingOperations());
        account.setAccountClosingDate(dto.getAccountClosingDate() != null ? Date.valueOf(dto.getAccountClosingDate()) : null);
    }

    //удаление кэша при обновлении
    @CacheEvict(value = {"allAccountsCache", "accountByIdCache"}, allEntries = true)
    @Override
    public Optional<AccountDto> update(Long id, AccountDto dto) {
        Optional<Account> existing = accountRepository.findById(id);
        if (existing.isPresent()) {
            Account account = existing.get();
            updateEntityFromDto(account, dto);
            Account saved = accountRepository.saveAndFlush(account);
            log.info("Updated account with id: {}", saved.getId());
            return Optional.ofNullable(accountMapper.toDto(saved));
        }
        log.info("Account not updates, cause it not exists");
        return Optional.empty();
    }

    //удаление кэша при удалении
    @CacheEvict(value = {"allAccountsCache", "accountByIdCache"}, allEntries = true)
    @Override
    public boolean delete(Long id) {
        Optional<Account> existing = accountRepository.findById(id);
        if (existing.isPresent()) {
            accountRepository.delete(existing.get());
            log.info("Account with id {} deleted:", id);
            return true;
        }
        log.info("Account not deleted");
        return false;
    }

    //удаление кэша при обновлении
    @CacheEvict(value = {"allAccountsCache", "accountByIdCache"}, allEntries = true)
    @Override
    public boolean deleteAll() {
        try {
            accountRepository.deleteAll();
            log.info("All accounts deleted");
            return true;
        } catch (ResourceNotFoundException e) {
            log.error("All accounts not deleted, cause: {}", e.getMessage());
            return false;
        }
    }

    //удаление кэша при обновлении
    @CacheEvict(value = {"allAccountsCache", "accountByIdCache"}, allEntries = true)
    @Override
    public boolean dropTable() {
        try {
            accountRepository.dropTable();
            log.info("Table accounts dropped");
        } catch (ResourceNotFoundException e) {
            log.warn("Table accounts not dropped, cause: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean createTable() {
        try {
            accountRepository.createTable();
            log.info("Table accounts created");
            return true;
        } catch (ResourceNotFoundException e) {
            log.warn("Table accounts not created, cause: {}", e.getMessage());
            return false;
        }
    }

    //удаление кэша при обновлении
    @CacheEvict(value = {"allAccountsCache", "accountByIdCache"}, allEntries = true)
    @Override
    public boolean initializeTable() {
        try {
            accountRepository.insertDefaultValues();
            log.info("Table accounts initialized");
            return true;
        } catch (ResourceNotFoundException e) {
            log.warn("Table accounts not initialized, cause: {}", e.getMessage());
            return false;
        }
    }
}
