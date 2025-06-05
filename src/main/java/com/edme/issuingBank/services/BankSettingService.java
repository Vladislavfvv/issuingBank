package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.BankSettingDto;
import com.edme.issuingBank.exceptions.ResourceNotFoundException;
import com.edme.issuingBank.mappers.BankSettingsMapper;
import com.edme.issuingBank.models.BankSetting;
import com.edme.issuingBank.repositories.BankSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankSettingService implements AbstractService<Long, BankSettingDto> {

    private final BankSettingRepository bankSettingRepository;
    private final BankSettingsMapper bankSettingsMapper;

    public BankSettingService(BankSettingRepository bankSettingRepository, BankSettingsMapper bankSettingsMapper) {
        this.bankSettingRepository = bankSettingRepository;
        this.bankSettingsMapper = bankSettingsMapper;
    }

    @Override
    public List<BankSettingDto> findAll() {
        return bankSettingRepository.findAll()
                .stream()
                .map(bankSettingsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BankSettingDto> findById(Long aLong) {
        return bankSettingRepository.findById(aLong)
                .map(bankSettingsMapper::toDto);
    }

    @Override
    @Transactional
    public Optional<BankSettingDto> save(BankSettingDto dto) {
        Optional<BankSetting> bankSettingOptional = bankSettingRepository.findBySetting(dto.getSetting());
        if (bankSettingOptional.isPresent()) {
            log.info("BankSetting {} already exists", dto.getSetting());
            return Optional.empty();
        }
        dto.setId(null);
        BankSetting save = bankSettingRepository.saveAndFlush(bankSettingsMapper.toEntity(dto));
        log.info("BankSetting with id {} saved", dto.getId());
        return Optional.ofNullable(bankSettingsMapper.toDto(save));
    }

    @Override
    @Transactional
    public Optional<BankSettingDto> update(Long id, BankSettingDto dto) {
        Optional<BankSetting> existing = bankSettingRepository.findById(id);
        if (existing.isPresent()) {
            BankSetting forSaving = existing.get();
            forSaving.setSetting(dto.getSetting());
            forSaving.setDescription(dto.getDescription());
            forSaving.setCurrentValue(dto.getCurrentValue());
            BankSetting saved = bankSettingRepository.saveAndFlush(forSaving);
            log.info("BankSetting with id {} updated", saved.getId());
            return Optional.ofNullable(bankSettingsMapper.toDto(saved));
        }
        log.info("BankSetting with id {} not found", id);
        return Optional.empty();
    }


    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<BankSetting> bankSettingOptional = bankSettingRepository.findById(id);
        if (bankSettingOptional.isEmpty()) {
            log.info("Bank setting with id {} not found", id);
            return false;
        }
        bankSettingRepository.delete(bankSettingOptional.get());
        log.info("BankSetting with id {} deleted", id);
        return true;
    }


    @Override
    @Transactional
    public boolean deleteAll() {
        try {
            bankSettingRepository.deleteAll();
            log.info("Bank settings deleted successfully");
            return true;
        } catch (ResourceNotFoundException e) {
            log.info("BankSettings could not be deleted, cause {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean dropTable() {
        try {
            bankSettingRepository.dropTable();
            log.info("Bank settings dropped successfully");
        } catch (ResourceNotFoundException e) {
            log.info("BankSettings table could not be dropped, cause {}", e.getMessage());
        }
        return false;
    }

    @Override
    @Transactional
    public boolean createTable() {
        try {
            bankSettingRepository.createTable();
            log.info("Bank settings created successfully");
        } catch (ResourceNotFoundException e) {
            log.info("BankSettings  table could not be created, cause {}", e.getMessage());
        }
        return false;
    }

    @Override
    @Transactional
    public boolean initializeTable() {
        try {
            bankSettingRepository.insertDefaultValues();
            log.info("Bank settings initialized successfully");
        } catch (ResourceNotFoundException e) {
            log.info("BankSettings table could not be initialized, cause {}", e.getMessage());
        }
        return false;
    }
}
