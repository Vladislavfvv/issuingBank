package com.edme.issuingBank.services;

import com.edme.issuingBank.dto.TransactionTypeDto;
import com.edme.issuingBank.mappers.TransactionTypeMapper;
import com.edme.issuingBank.models.TransactionType;
import com.edme.issuingBank.repositories.TransactionTypesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionTypesServiceTest {
    @Mock
    private TransactionTypesRepository ttRepository;

    @Mock
    private TransactionTypeMapper ttMapper;

    @InjectMocks
    private TransactionTypesService service;

    private TransactionTypeDto dto;
    private TransactionType entity;
    private  TransactionType entity1;
    private  TransactionType entity2;
    private  TransactionTypeDto dto1;
    private  TransactionTypeDto dto2;



    @BeforeEach
    void setUp() {
        dto = new TransactionTypeDto();
        dto.setId(1L);
        dto.setTransactionTypeName("Покупка");

        entity = new TransactionType();
        entity.setId(1L);
        entity.setTransactionTypeName("Покупка");
        entity1 = new TransactionType(1L, "Оплата");
        entity2 = new TransactionType(2L, "Возврат");

        dto1 = new TransactionTypeDto(1L, "Оплата");
        dto2 = new TransactionTypeDto(2L, "Возврат");
    }

    @Test
    void testFindAll_ReturnsDtoListWithMultipleItems() {
        List<TransactionType> entities = List.of(entity1, entity2);
        List<TransactionTypeDto> expectedDtos = List.of(dto1, dto2);

        when(ttRepository.findAll()).thenReturn(entities);
        when(ttMapper.toDto(entity1)).thenReturn(dto1);
        when(ttMapper.toDto(entity2)).thenReturn(dto2);

        List<TransactionTypeDto> result = service.findAll();

        // Проверяем размер списка
        assertEquals(2, result.size());

        // Проверяем каждый элемент по полям
        for (int i = 0; i < expectedDtos.size(); i++) {
            assertEquals(expectedDtos.get(i).getId(), result.get(i).getId());
            assertEquals(expectedDtos.get(i).getTransactionTypeName(), result.get(i).getTransactionTypeName());
        }
    }

    @Test
    void testFindAll_ReturnsDtoList() {
        when(ttRepository.findAll()).thenReturn(List.of(entity));
        when(ttMapper.toDto(entity)).thenReturn(dto);

        List<TransactionTypeDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals(dto.getTransactionTypeName(), result.get(0).getTransactionTypeName());
    }

    @Test
    void testFindById_ReturnsDto() {
        when(ttRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(ttMapper.toDto(entity)).thenReturn(dto);

        Optional<TransactionTypeDto> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(dto.getTransactionTypeName(), result.get().getTransactionTypeName());
    }

    @Test
    void testSave_WhenNotExists_SavesAndReturnsDto() {
        TransactionType saved = new TransactionType();
        saved.setId(2L);
        saved.setTransactionTypeName("Продажа");

        TransactionTypeDto newDto = new TransactionTypeDto();
        newDto.setId(2L);
        newDto.setTransactionTypeName("Продажа");

        when(ttRepository.findById(2L)).thenReturn(Optional.empty());
        when(ttMapper.toEntity(newDto)).thenReturn(saved);
        when(ttRepository.saveAndFlush(saved)).thenReturn(saved);
        when(ttMapper.toDto(saved)).thenReturn(newDto);

        Optional<TransactionTypeDto> result = service.save(newDto);

        assertTrue(result.isPresent());
        assertEquals("Продажа", result.get().getTransactionTypeName());
    }

    @Test
    void testSave_WhenExists_ReturnsEmpty() {
        when(ttRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<TransactionTypeDto> result = service.save(dto);

        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdate_WhenExists_UpdatesAndReturnsDto() {
        when(ttRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(ttMapper.toEntity(dto)).thenReturn(entity);
        when(ttRepository.saveAndFlush(entity)).thenReturn(entity);
        when(ttMapper.toDto(entity)).thenReturn(dto);

        Optional<TransactionTypeDto> result = service.update(1L, dto);

        assertTrue(result.isPresent());
        assertEquals(dto.getTransactionTypeName(), result.get().getTransactionTypeName());
    }

    @Test
    void testUpdate_WhenNotExists_ReturnsEmpty() {
        when(ttRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<TransactionTypeDto> result = service.update(99L, dto);

        assertTrue(result.isEmpty());
    }

    @Test
    void testDelete_WhenExists_ReturnsTrue() {
        when(ttRepository.findById(1L)).thenReturn(Optional.of(entity));

        boolean result = service.delete(1L);

        assertTrue(result);
        verify(ttRepository).deleteById(1L);
    }

    @Test
    void testDelete_WhenNotExists_ReturnsFalse() {
        when(ttRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = service.delete(1L);

        assertFalse(result);
    }

    @Test
    void testDeleteAll_ReturnsTrue() {
        boolean result = service.deleteAll();
        assertTrue(result);
        verify(ttRepository).deleteAll();
    }

    @Test
    void testCreateTable_LogsAndReturnsFalse() {
        boolean result = service.createTable();
        assertFalse(result);
        verify(ttRepository).createTable();
    }

    @Test
    void testDropTable_LogsAndReturnsFalse() {
        boolean result = service.dropTable();
        assertFalse(result);
        verify(ttRepository).dropTable();
    }

    @Test
    void testInitializeTable_LogsAndReturnsFalse() {
        boolean result = service.initializeTable();
        assertFalse(result);
        verify(ttRepository).insertDefaultValues();
    }


}