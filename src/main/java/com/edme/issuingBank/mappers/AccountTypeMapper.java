package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.AccountTypeDto;
import com.edme.issuingBank.models.AccountType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {

    AccountType toEntity(AccountTypeDto dto);
    AccountTypeDto toDto(AccountType accountType);
}
