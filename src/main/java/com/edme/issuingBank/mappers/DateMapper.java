package com.edme.issuingBank.mappers;

import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface DateMapper {

    default Timestamp map(OffsetDateTime dateTime) {
        return dateTime == null ? null : Timestamp.from(dateTime.toInstant());
    }

    default OffsetDateTime map(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant().atOffset(OffsetDateTime.now().getOffset());
    }
}
