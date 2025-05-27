package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.PaymentSystemDto;
import com.edme.issuingBank.models.PaymentSystem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentSystemMapper {
    PaymentSystemDto toDto(PaymentSystem paymentSystem);
    PaymentSystem toEntity(PaymentSystemDto paymentSystemDto);
}
