package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.ClientDto;
import com.edme.issuingBank.models.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto toDto(Client client);
    Client toEntity(ClientDto clientDto);

}
