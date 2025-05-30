package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.*;
import com.edme.issuingBank.exceptions.ResourceNotFoundException;
import com.edme.issuingBank.mappers.*;
import com.edme.issuingBank.models.*;
import com.edme.issuingBank.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountService implements AbstractService<Long, AccountDto> {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ClientService clientService;
    private final CurrencyService currencyService;
    private final AccountTypeService accountTypeService;
    private final AccountTypeMapper accountTypeMapper;
    private final ClientMapper clientMapper;
    private final CurrencyMapper currencyMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, ClientService clientService, CurrencyService currencyService, AccountTypeService accountTypeService, AccountTypeMapper accountTypeMapper, ClientMapper clientMapper, CurrencyMapper currencyMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.clientService = clientService;
        this.currencyService = currencyService;
        this.accountTypeService = accountTypeService;
        this.accountTypeMapper = accountTypeMapper;
        this.clientMapper = clientMapper;
        this.currencyMapper = currencyMapper;
    }


    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccountDto> findById(Long aLong) {
        return accountRepository.findById(aLong)
                .map(accountMapper::toDto);
    }

    @Override
    @Transactional
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
        account.setAccountType(accountTypeService.findById(dto.getAccountTypeId().getId()).map(accountTypeMapper::toEntity).orElseThrow());
        account.setClient(clientService.findById(dto.getClientId().getId()).map(clientMapper::toEntity).orElseThrow());
        account.setCurrency(currencyService.findById(dto.getCurrencyId().getId()).map(currencyMapper::toEntity).orElseThrow());
        account.setAccountOpeningDate(Date.valueOf(dto.getAccountOpeningDate()));
        account.setSuspendingOperations(dto.isSuspendingOperations());
        account.setAccountClosingDate(dto.getAccountClosingDate() != null ? Date.valueOf(dto.getAccountClosingDate()) : null);
    }
    @Override
    @Transactional
    public Optional<AccountDto> update(Long id, AccountDto dto) {
        Optional<Account> existing = accountRepository.findById(id);
        if (existing.isPresent()) {
            Account account = existing.get();
            updateEntityFromDto(account, dto);
//            account.setAccountNumber(dto.getAccountNumber());
//            account.setBalance(dto.getBalance());
//            account.setAccountType(accountTypeService.findById(dto.getAccountTypeId().getId()).map(accountTypeMapper::toEntity).orElseThrow());
//            account.setClient(clientService.findById(dto.getClientId().getId()).map(clientMapper::toEntity).orElseThrow());
//            account.setCurrency(currencyService.findById(dto.getCurrencyId().getId()).map(currencyMapper::toEntity).orElseThrow());
//            account.setAccountOpeningDate(Date.valueOf(dto.getAccountOpeningDate()));
//            account.setSuspendingOperations(dto.isSuspendingOperations());
//            account.setAccountClosingDate(dto.getAccountClosingDate() != null ? Date.valueOf(dto.getAccountClosingDate()) : null);
            Account saved = accountRepository.saveAndFlush(account);
            log.info("Updated account id: {}", saved.getId());
            return Optional.ofNullable(accountMapper.toDto(saved));
        }
        log.info("Account not updates, cause it not exists");
        return Optional.empty();
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public boolean deleteAll() {
        try {
            accountRepository.deleteAll();
            log.info("All accounts deleted");
            return true;
        } catch (ResourceNotFoundException e) {
            log.info("All accounts not deleted, cause: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public void dropTable() {
        try {
            accountRepository.dropTable();
            log.info("Table accounts dropped");
        } catch (ResourceNotFoundException e) {
            log.info("Table accounts not dropped, cause: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void createTable() {
        try {
            accountRepository.createTable();
            log.info("Table accounts created");
        } catch (ResourceNotFoundException e) {
            log.info("Table accounts not created, cause: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void initializeTable() {
        try {
            accountRepository.insertDefaultValues();
            log.info("Table accounts initialized");
        } catch (ResourceNotFoundException e) {
            log.info("Table accounts not initialized, cause: {}", e.getMessage());
        }
    }
}
