package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.PaymentSystemDto;
import com.edme.issuingBank.exceptions.ResourceNotFoundException;
import com.edme.issuingBank.mappers.PaymentSystemMapper;
import com.edme.issuingBank.models.PaymentSystem;
import com.edme.issuingBank.repositories.PaymentSystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentSystemService implements AbstractService<Long, PaymentSystemDto> {

    private final PaymentSystemRepository paymentSystemRepository;
    private final PaymentSystemMapper paymentSystemMapper;

    public PaymentSystemService(PaymentSystemRepository paymentSystemRepository, PaymentSystemMapper paymentSystemMapper) {
        this.paymentSystemRepository = paymentSystemRepository;
        this.paymentSystemMapper = paymentSystemMapper;
    }


    @Override
    public List<PaymentSystemDto> findAll() {
        return paymentSystemRepository.findAll()
                .stream()
                .map(paymentSystemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentSystemDto> findById(Long aLong) {
        return paymentSystemRepository.findById(aLong)
                .map(paymentSystemMapper::toDto);
    }

    @Override
    @Transactional
    public Optional<PaymentSystemDto> save(PaymentSystemDto dto) {
        Optional<PaymentSystem> exist = paymentSystemRepository.findById(dto.getId());
        if (exist.isPresent()) {
            log.info("Payment system already exists");
            return Optional.empty();
        }
        dto.setId(null);
        PaymentSystem saved = paymentSystemRepository.saveAndFlush(paymentSystemMapper.toEntity(dto));
        log.info("Payment system saved");
        return Optional.of(paymentSystemMapper.toDto(saved));
    }

    @Override
    @Transactional
    public Optional<PaymentSystemDto> update(Long id, PaymentSystemDto dto) {
        Optional<PaymentSystem> exist = paymentSystemRepository.findById(id);
        if (exist.isEmpty()) {
            log.info("PaymentSystem does not exists");
            return Optional.empty();
        }
        PaymentSystem payment = exist.get();
        payment.setPaymentSystemName(dto.getPaymentSystemName());
        payment.setFirstDigitBin(dto.getFirstDigitBin());
        PaymentSystem updated = paymentSystemRepository.saveAndFlush(payment);
        log.info("PaymentSystem {} updated", updated.getPaymentSystemName());
        return Optional.of(paymentSystemMapper.toDto(updated));
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<PaymentSystem> exist = paymentSystemRepository.findById(id);
        if (exist.isEmpty()) {
            log.info("PaymentSystem does not exists");
            return false;
        }
        paymentSystemRepository.delete(exist.get());
        log.info("PaymentSystem {} deleted", exist.get().getPaymentSystemName());
        return false;
    }

    @Override
    @Transactional
    public boolean deleteAll() {
        try {
            paymentSystemRepository.deleteAll();
            log.info("All paymentSystems deleted");
            return true;
        } catch (ResourceNotFoundException e) {
            log.info("Error deleting all PaymentSystems, cause: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public void dropTable() {
        try {
            paymentSystemRepository.dropTable();
            log.info("PaymentSystems table dropped");
        } catch (ResourceNotFoundException e) {
            log.info("Error dropping PaymentSystems table, cause: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void createTable() {
        try {
            paymentSystemRepository.createTable();
            log.info("PaymentSystems table created");
        } catch (ResourceNotFoundException e) {
            log.info("Error creating PaymentSystems, cause: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void initializeTable() {
        try {
            paymentSystemRepository.insertDefaultValues();
            log.info("PaymentSystems table initialized");
        } catch (ResourceNotFoundException e) {
            log.info("Error initializing PaymentSystems, cause: {}", e.getMessage());
        }
    }
}
