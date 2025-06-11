package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.TransactionTypeDto;
import com.edme.issuingBank.models.TransactionType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionTypeMapper {
    TransactionTypeDto toDto(TransactionType transactionType);

    TransactionType toEntity(TransactionTypeDto transactionTypeDto);
}
