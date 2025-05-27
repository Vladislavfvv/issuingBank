package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.CurrencyDto;
import com.edme.issuingBank.models.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyDto toDto(Currency currency);
    Currency toEntity(CurrencyDto dto);
}
