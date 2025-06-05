package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.AccountTypeDto;
import com.edme.issuingBank.exceptions.ResourceNotFoundException;
import com.edme.issuingBank.mappers.AccountTypeMapper;
import com.edme.issuingBank.models.AccountType;
import com.edme.issuingBank.repositories.AccountTypeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountTypeService implements AbstractService<Long, AccountTypeDto> {

    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;

    public AccountTypeService(AccountTypeRepository accountTypeRepository, AccountTypeMapper accountTypeMapper) {
        this.accountTypeRepository = accountTypeRepository;
        this.accountTypeMapper = accountTypeMapper;
    }

    @Override
    public List<AccountTypeDto> findAll() {
        return accountTypeRepository.findAll()
                .stream()
                .map(accountTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccountTypeDto> findById(Long aLong) {
        return accountTypeRepository.findById(aLong)
                .map(accountTypeMapper::toDto);
    }

    @Override
    @Transactional
    public Optional<AccountTypeDto> save(AccountTypeDto dto) {
        Optional<AccountTypeDto> existing = accountTypeRepository.findById(dto.getId())
                .map(accountTypeMapper::toDto);
        if (existing.isPresent()) {
            log.info("Account already exists {}", existing.get());
            return Optional.empty();
        }
        AccountType accountType = accountTypeMapper.toEntity(dto);
        accountType.setId(null);// Явно обнуляем ID, чтобы JPA сгенерировала новый
        AccountType saved = accountTypeRepository.saveAndFlush(accountType);
        log.info("Saved account in table {}", saved);
        return Optional.ofNullable(accountTypeMapper.toDto(saved));
    }

    @Override
    @Transactional
    public Optional<AccountTypeDto> update(Long id, AccountTypeDto dto) {
        Optional<AccountTypeDto> existing = accountTypeRepository.findById(id).map(accountTypeMapper::toDto);
        if (existing.isPresent()) {
            AccountTypeDto existingDto = existing.get();
            existingDto.setAccountTypeName(dto.getAccountTypeName());
            accountTypeRepository.save(accountTypeMapper.toEntity(dto));
            log.info("Updated account in table {}", accountTypeMapper.toEntity(existingDto));
            return Optional.of(existingDto);
        }
        log.info("Account with id {} not found", id);
        return Optional.empty();
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<AccountTypeDto> existing = accountTypeRepository.findById(id)
                .map(accountTypeMapper::toDto);
        if (existing.isPresent()) {
            accountTypeRepository.deleteById(id);
            log.info("Deleted account from table {}", accountTypeMapper.toEntity(existing.get()));
            return true;
        }
        log.info("Account type with id {} not found", id);
        return false;
    }

    @Override
    @Transactional
    public boolean deleteAll() {
        try {
            accountTypeRepository.deleteAll();
            log.info("Deleted all account from table");
            return true;
        } catch (ResourceNotFoundException e) {
            log.error("Error while deleting all accounts from table", e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean dropTable() {
        try {
            accountTypeRepository.dropTable();
            log.info("Dropped Account table");
            return true;

        } catch (ResourceNotFoundException e) {
            log.error("Error while dropping Account table", e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean createTable() {
        try {
            accountTypeRepository.createTable();
            log.info("Created Account table");
        } catch (ResourceNotFoundException e) {
            log.error("Error while creating Account table", e);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean initializeTable() {
        try {
            accountTypeRepository.insertDefaultValues();
            log.info("Inserted default values into table {}", accountTypeRepository.count());
        } catch (ResourceNotFoundException e) {
            log.error("Error while inserting default values", e);
        }

        return false;
    }
}
