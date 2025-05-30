package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.CurrencyDto;
import com.edme.issuingBank.exceptions.ResourceNotFoundException;
import com.edme.issuingBank.mappers.CurrencyMapper;
import com.edme.issuingBank.models.Currency;
import com.edme.issuingBank.repositories.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CurrencyService implements AbstractService<Long, CurrencyDto>{

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public CurrencyService(CurrencyRepository currencyRepository, CurrencyMapper currencyMapper) {
        this.currencyRepository = currencyRepository;
        this.currencyMapper = currencyMapper;
    }

    @Override
    public List<CurrencyDto> findAll() {
        return currencyRepository.findAll()
                .stream()
                .map(currencyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CurrencyDto> findById(Long aLong) {
        return currencyRepository.findById(aLong)
                .map(currencyMapper::toDto);
    }

    @Override
    @Transactional
    public Optional<CurrencyDto> save(CurrencyDto dto) {
        Optional<Currency> existing = currencyRepository.findById(dto.getId());
        if (existing.isPresent()) {
            log.info("Currency with id {} already exists", dto.getId());
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<CurrencyDto> update(Long id, CurrencyDto dto) {
        Optional<Currency> existing = currencyRepository.findById(id);
        if (existing.isEmpty()) {
            log.info("Currency with id {} does not exist", id);
            return Optional.empty();
        }
        Currency currency = existing.get();
        currency.setCurrencyDigitalCode(dto.getCurrencyDigitalCode());
        currency.setCurrencyLetterCode(dto.getCurrencyLetterCode());
        currency.setCurrencyDigitalCodeAccount(dto.getCurrencyDigitalCodeAccount());
        currency.setCurrencyName(dto.getCurrencyName());
        Currency updatedCurrency = currencyRepository.saveAndFlush(currency);
        log.info("Currency with id {} updated", id);
        return Optional.of(currencyMapper.toDto(updatedCurrency));
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<Currency> existing = currencyRepository.findById(id);
        if (existing.isPresent()) {
            currencyRepository.deleteById(id);
            return true;
        }
        log.info("Currency with id {} does not exist", id);
        return false;
    }

    @Override
    @Transactional
    public boolean deleteAll() {
        try {
            currencyRepository.deleteAll();
            log.info("All currencies have been deleted");
            return true;
        } catch (ResourceNotFoundException e) {
            log.info("All currencies have not been deleted, cause {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public void dropTable() {
        try {
            currencyRepository.deleteAll();
            log.info("Table currencies have been dropped");
        } catch (ResourceNotFoundException e) {
            log.info("Table currencies have not been dropped, cause {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void createTable() {
        try {
            currencyRepository.deleteAll();
            log.info("Table currencies have been created");
        } catch (ResourceNotFoundException e) {
            log.info("Table currencies have not been created, cause {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void initializeTable() {
        try{
            currencyRepository.deleteAll();
            log.info("Table currencies have been initialized");
        }catch (ResourceNotFoundException e){
            log.info("Table currencies have not been initialized, cause {}", e.getMessage());
        }
    }
}
