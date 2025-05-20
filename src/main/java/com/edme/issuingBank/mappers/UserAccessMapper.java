package com.edme.issuingBank.mappers;


import com.edme.issuingBank.dto.UserAccessDto;
import com.edme.issuingBank.models.UserAccess;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccessMapper {
    UserAccess toEntity(UserAccessDto userAccessDto);
    UserAccessDto toDto(UserAccess userAccess);
}
