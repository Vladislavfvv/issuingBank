package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.BankSettingDto;
import com.edme.issuingBank.models.BankSetting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankSettingsMapper {
    BankSetting toEntity(BankSettingDto dto);
    BankSettingDto toDto(BankSetting entity);
}
