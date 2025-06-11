package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.TransactionExchangeIbDto;
import com.edme.issuingBank.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        TransactionTypeMapper.class,
        AccountMapper.class,
        DateMapper.class
})
public interface TransactionExchangeIbMapper {
    @Mapping(source = "transactionType", target = "transactionType.id")
    @Mapping(source = "account", target = "account.id")
    Transaction toEntity(TransactionExchangeIbDto dto);

    @Mapping(source = "transactionType.id", target = "transactionType")
    @Mapping(source = "account.id", target = "account")
    TransactionExchangeIbDto toDto(Transaction entity);
}
