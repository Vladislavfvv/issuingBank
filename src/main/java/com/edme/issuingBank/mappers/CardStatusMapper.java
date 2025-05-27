package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.CardStatusDto;
import com.edme.issuingBank.models.CardStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardStatusMapper {
    CardStatusDto toDto(CardStatus cardStatus);
    CardStatus toEntity(CardStatusDto cardStatusDto);
}
