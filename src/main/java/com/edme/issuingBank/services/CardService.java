package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.CardDto;
import com.edme.issuingBank.exceptions.ResourceNotFoundException;
import com.edme.issuingBank.mappers.*;
import com.edme.issuingBank.models.Card;
import com.edme.issuingBank.repositories.CardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class CardService implements AbstractService<Long, CardDto> {
    private final CardRepository cardRepository;
    private final CardStatusService cardStatusService;
    private final PaymentSystemService paymentSystemService;
    private final AccountService accountService;
    private final ClientService clientService;
    private final CardMapper cardMapper;
    private final CardStatusMapper cardStatusMapper;
    private final PaymentSystemMapper paymentSystemMapper;
    private final AccountMapper accountMapper;
    private final ClientMapper clientMapper;


    @Override
    @Transactional(readOnly = true)
    public List<CardDto> findAll() {
        return cardRepository.findAll()
                .stream()
                .map(cardMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CardDto> findById(Long aLong) {
        return cardRepository.findById(aLong)
                .map(cardMapper::toDto);
    }

    @Override
    public Optional<CardDto> save(CardDto dto) {
        Optional<Card> exists = cardRepository.findById(dto.getId());
        if (exists.isPresent()) {
            log.info("Card already exists: {}", exists.get());
            return Optional.empty();
        }
        dto.setId(null);
        Card saved = cardRepository.saveAndFlush(cardMapper.toEntity(dto));
        log.info("Saved Card: {}", saved);
        return Optional.of(cardMapper.toDto(saved));
    }

    @Override
    public Optional<CardDto> update(Long id, CardDto dto) {
        Optional<Card> exists = cardRepository.findById(dto.getId());
        if (exists.isEmpty()) {
            log.info("Card not updated because its not exist: {}", dto);
            return Optional.empty();
        }
        Card card = exists.get();

        card.setCardNumber(dto.getCardNumber());
        card.setHolderName(dto.getHolderName());
        card.setCardStatus(cardStatusMapper.toEntity(dto.getCardStatus()));
        card.setAccount(accountMapper.toEntity(dto.getAccount()));
        card.setPaymentSystem(paymentSystemMapper.toEntity(dto.getPaymentSystem()));
        card.setClient(clientMapper.toEntity(dto.getClient()));
        card.setReceivedFromProcessingCenter(dto.getReceivedFromProcessingCenter());
        card.setSentToProcessingCenter(dto.getSentToProcessingCenter());
        Card saved = cardRepository.saveAndFlush(card);

        log.info("Updated Card: {} with id: {}", saved, id);
        return Optional.ofNullable(cardMapper.toDto(saved));
    }

    @Override
    public boolean delete(Long id) {
        Optional<Card> exists = cardRepository.findById(id);
        if (exists.isEmpty()) {
            log.info("Card not deleted because its not exist: {}", id);
            return false;
        }
        cardRepository.delete(exists.get());
        log.info("Card deleted: {}", id);
        return false;
    }

    @Override
    public boolean deleteAll() {
        try {
            cardRepository.deleteAll();
            log.info("Table cards deleted.");
            return true;
        } catch (ResourceNotFoundException e) {
            log.info("Table cards not found.");
            return false;
        }
    }

    @Override
    public boolean dropTable() {
        try {
            cardRepository.dropTable();
            log.info("Table cards dropped.");
        } catch (ResourceNotFoundException e) {
            log.info("Table cards not dropped, cause {}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean createTable() {
        try {
            cardRepository.createTable();
            log.info("Table cards created.");
        } catch (ResourceNotFoundException e) {
            log.info("Table cards not created, cause {}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean initializeTable() {
        try {
            cardRepository.insertDefaultValues();
            log.info("Table cards initialized.");
        } catch (ResourceNotFoundException e) {
            log.info("Table cards not initialized, cause {}", e.getMessage());
        }
        return false;
    }
}
