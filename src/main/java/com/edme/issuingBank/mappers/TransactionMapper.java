package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.TransactionDto;
import com.edme.issuingBank.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {TransactionType.class,
        AccountMapper.class,
        CurrencyMapper.class,
        AccountTypeMapper.class,
        ClientMapper.class})
public interface TransactionMapper {

    //    @Mapping(source = "transaction.transactionType.id", target = "transactionTypeId")
//    @Mapping(source = "transaction.account.id", target = "accountId")
// Entity → DTO
//    @Mapping(target = "transactionType", source = "transactionType")
//    @Mapping(target = "account", source = "account")
    TransactionDto toDto(Transaction transaction);


    //    @Mapping(source = "transactionTypeId", target = "transactionType.id")
//    @Mapping(source = "accountId", target = "account.id")
// DTO → Entity
//    @Mapping(target = "transactionType", source = "transactionType", qualifiedByName = "mapTransactionType")
//    @Mapping(target = "account", source = "account", qualifiedByName = "mapAccount")
    Transaction toEntity(TransactionDto dto);


//    @Named("mapTransactionType")
//    default TransactionType mapTransactionType(Long id) {
//        if (id == null) return null;
//        TransactionType type = new TransactionType();
//        type.setId(id);
//        return type;
//    }
//
//    @Named("mapAccount")
//    default Account mapAccount(Long id) {
//        if (id == null) return null;
//        Account account = new Account();
//        account.setId(id);
//        return account;
//    }
}
