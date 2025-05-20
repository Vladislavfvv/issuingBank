package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.UserAccessDto;
import com.edme.issuingBank.mappers.UserAccessMapper;
import com.edme.issuingBank.models.UserAccess;
import com.edme.issuingBank.repositories.UserAccessRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service

public class UserAccessService {
    private final UserAccessRepository userAccessRepository;
    private final UserAccessMapper userAccessMapper;

    public UserAccessService(UserAccessRepository userAccessRepository, UserAccessMapper userAccessMapper) {
        this.userAccessRepository = userAccessRepository;
        this.userAccessMapper = userAccessMapper;
    }

    public List<UserAccessDto> findAll() {
        return userAccessRepository.findAll()
                .stream().map(userAccessMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserAccessDto> findById(Long id) {
        return userAccessRepository.findById(id)
                .map(userAccessMapper::toDto);
    }

    @Transactional
    public Optional<UserAccessDto> save(UserAccessDto dto) {
        // Optional<UserAccessDto> exiting = userAccessRepository.findById(dto.getId()).map(userAccessMapper::toDto);
        Optional<UserAccessDto> existing = userAccessRepository.findByUserLogin(dto.getUserLogin()).map(userAccessMapper::toDto);
        if (existing.isPresent()) {
            log.info("User access already exists with id: {}", dto.getId());
            return Optional.empty();
        } else {
            dto.setId(null);
            UserAccess saved = userAccessRepository.saveAndFlush(userAccessMapper.toEntity(dto));
            log.info("User access successfully saved");
            return Optional.ofNullable(userAccessMapper.toDto(saved));
        }
    }

    @Transactional
    public Optional<UserAccessDto> update(Long id, UserAccessDto dto) {
        Optional<UserAccessDto> exiting = userAccessRepository.findById(id).map(userAccessMapper::toDto);
        if (exiting.isPresent()) {
            UserAccessDto exitingDto = exiting.get();
            // exiting.get().setId(dto.getId());
            // exitingDto.setId(dto.getId());
            exitingDto.setUserLogin(dto.getUserLogin());
            exitingDto.setUserPassword(dto.getUserPassword());
            exitingDto.setFullName(dto.getFullName());
            exitingDto.setUserRole(dto.getUserRole());
            userAccessRepository.save(userAccessMapper.toEntity(exitingDto));
            // log.info("UserAccess with id: {} successfully updated", exitingDto.getId());
            log.info("UserAccess with id: {} successfully updated", userAccessMapper.toEntity(exitingDto));
            return Optional.of(exitingDto);
        } else {
            log.info("User access not found with id: {}", id);
            return Optional.empty();
        }
    }


    @Transactional
    public boolean delete(Long id) {
        Optional<UserAccessDto> exiting = findById(id);
        if (exiting.isPresent()) {
            userAccessRepository.deleteById(id);
            log.info("UserAccess with id: {} successfully deleted", id);
            return true;
        } else {
            log.info("User access already exists with id: {}", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll() {
        try {
            userAccessRepository.deleteAll();
            log.info("UserAccess deleted successfully");
            return true;
        } catch (Exception e) {
            log.info("UserAccess could not be deleted");
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try {
            userAccessRepository.deleteAll();
            log.info("UserAccess dropped successfully");
            return true;
        } catch (Exception e) {
            log.info("UserAccess could not be dropped");
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try {
            userAccessRepository.createTable();
            log.info("UserAccess created successfully");
            return true;
        } catch (Exception e) {
            log.error("Error creating Table UserAccess {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
        if (!createTable()) {
            return false;
        }
        try {
            userAccessRepository.insertDefaultValues();
            log.info("UserAccess initialized successfully");
            return true;
        } catch (Exception e) {
            log.error("Error initializing Table UserAccess {}", e.getMessage());
            return false;
        }
    }
}
