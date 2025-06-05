package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.CardStatusDto;
import com.edme.issuingBank.exceptions.ResourceNotFoundException;
import com.edme.issuingBank.mappers.CardStatusMapper;
import com.edme.issuingBank.models.CardStatus;
import com.edme.issuingBank.repositories.CardStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CardStatusService implements AbstractService<Long, CardStatusDto> {

    private final CardStatusRepository cardStatusRepository;
    private final CardStatusMapper cardStatusMapper;

    public CardStatusService(CardStatusRepository cardStatusRepository, CardStatusMapper cardStatusMapper) {
        this.cardStatusRepository = cardStatusRepository;
        this.cardStatusMapper = cardStatusMapper;
    }

    @Override
    public List<CardStatusDto> findAll() {
        return cardStatusRepository.findAll()
                .stream()
                .map(cardStatusMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CardStatusDto> findById(Long aLong) {
        return cardStatusRepository.findById(aLong)
                .map(cardStatusMapper::toDto);
    }

    @Override
    @Transactional
    public Optional<CardStatusDto> save(CardStatusDto dto) {
        Optional<CardStatus> exist = cardStatusRepository.findById(dto.getId());
        if (exist.isPresent()) {
            log.info("CardStatus already exists with id: {} ", exist.get().getId());
            return Optional.empty();
        }
        dto.setId(null);
        CardStatus saved = cardStatusRepository.saveAndFlush(cardStatusMapper.toEntity(dto));
        log.info("CardStatus saved with id: {} ", saved.getId());
        return Optional.ofNullable(cardStatusMapper.toDto(saved));

    }

    @Override
    @Transactional
    public Optional<CardStatusDto> update(Long id, CardStatusDto dto) {
        Optional<CardStatus> exist = cardStatusRepository.findById(id);
        if (exist.isEmpty()) {
            log.info("CardStatus does not exists with id: {} ", id);
            return Optional.empty();
        }
        CardStatus cardStatus = exist.get();
        cardStatus.setCardStatusName(dto.getCardStatusName());
        CardStatus saved = cardStatusRepository.saveAndFlush(cardStatus);
        log.info("CardStatus saved with id: {} ", saved.getId());
        return Optional.ofNullable(cardStatusMapper.toDto(saved));
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<CardStatus> exist = cardStatusRepository.findById(id);
        if (exist.isEmpty()) {
            log.info("CardStatus does not exists with id: {}", id);
            return false;
        }
        cardStatusRepository.delete(exist.get());
        log.info("CardStatus deleted with id: {}", id);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteAll() {
        try {
            cardStatusRepository.deleteAll();
            log.info("All cardStatuses deleted");
            return true;
        } catch (ResourceNotFoundException e) {
            log.info("CardStatuses not deleted, cause {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean dropTable() {
        try {
            cardStatusRepository.dropTable();
            log.info("Table cardStatuses dropped");
        } catch (ResourceNotFoundException e) {
            log.info("Table cardStatuses not dropped, cause {}", e.getMessage());
        }
        return false;
    }

    @Override
    @Transactional
    public boolean createTable() {
        try {
            cardStatusRepository.createTable();
            log.info("Table cardStatuses created");
        }catch (ResourceNotFoundException e) {
            log.info("Table cardStatuses not created, cause {}", e.getMessage());
        }
        return false;
    }

    @Override
    @Transactional
    public boolean initializeTable() {
        try{
            cardStatusRepository.createTable();
            log.info("Table cardStatuses initialized");
        }catch(ResourceNotFoundException e){
            log.info("Table cardStatuses not initialized, cause {}", e.getMessage());
        }
        return false;
    }
}
