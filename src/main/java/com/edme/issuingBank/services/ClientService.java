package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.ClientDto;
import com.edme.issuingBank.exceptions.ResourceNotFoundException;
import com.edme.issuingBank.mappers.ClientMapper;
import com.edme.issuingBank.models.Client;
import com.edme.issuingBank.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientService implements AbstractService<Long, ClientDto> {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClientDto> findById(Long aLong) {
        return clientRepository.findById(aLong)
                .map(clientMapper::toDto);
    }

    @Override
    @Transactional
    public Optional<ClientDto> save(ClientDto dto) {
        Optional<Client> existing = clientRepository.findByDocument(dto.getDocument());
        if (existing.isPresent()) {
            log.info("Client with id {} already exists", dto.getId());
            return Optional.empty();
        }
        dto.setId(null);
        Client saved = clientRepository.saveAndFlush(clientMapper.toEntity(dto));
        log.info("Saved new client {}", saved);
        return Optional.ofNullable(clientMapper.toDto(saved));
    }

    @Override
    @Transactional
    public Optional<ClientDto> update(Long id, ClientDto dto) {
        Optional<Client> existing = clientRepository.findById(id);
        if (existing.isEmpty()) {
            log.info("Client with id {} does not exist", id);
            return Optional.empty();
        }
        Client client = existing.get();
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setMiddleName(dto.getMiddleName());
        client.setBirthDate(dto.getBirthDate());
        client.setPhone(dto.getPhone());
        client.setDocument(dto.getDocument());
        client.setAddress(dto.getAddress());
        client.setEmail(dto.getEmail());
        Client saved = clientRepository.saveAndFlush(client);
        log.info("Saved new client {}", saved);
        return Optional.ofNullable(clientMapper.toDto(saved));
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<Client> existing = clientRepository.findById(id);
        if (existing.isEmpty()) {
            log.info("Client with id {} does not exist", id);
            return false;
        }
        clientRepository.delete(existing.get());
        log.info("Client {} successfully deleted", existing.get());
        return true;
    }

    @Override
    @Transactional
    public boolean deleteAll() {
        try {
            clientRepository.deleteAll();
            log.info("Client successfully deleted");
            return true;
        } catch (ResourceNotFoundException e) {
            log.info("Clients could not be deleted, cause {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public void dropTable() {
        try {
            clientRepository.dropTable();
            log.info("Client successfully dropped");
        } catch (ResourceNotFoundException e) {
            log.info("Clients Table could not be dropped, cause {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void createTable() {
        try {
            clientRepository.createTable();
            log.info("Client successfully created");
        } catch (ResourceNotFoundException e) {
            log.info("Clients Table could not be created, cause {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void initializeTable() {
        try {
            clientRepository.insertDefaultValues();
            log.info("Clients table successfully initialized");
        }catch (ResourceNotFoundException e) {
            log.info("Clients table could not be initialized, cause {}", e.getMessage());
        }
    }
}
