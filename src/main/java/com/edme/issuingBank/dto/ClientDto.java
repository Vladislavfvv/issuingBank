package com.edme.issuingBank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientDto {
    @Schema(description = "Unique account identifier", example = "123")
    private Long id;

    @NotNull(message = "lastName is required")
    @Size(max = 100, message = "lastName must be at most 100 characters")
    @Schema(description = "lastName name", example = "Ivanov")
    private String lastName;

    @NotNull(message = "firstName is required")
    @Size(max = 100, message = "firstName must be at most 100 characters")
    @Schema(description = "firstName name", example = "Ivan")
    private String firstName;

    @NotNull(message = "middleName is required")
    @Size(max = 100, message = "middleName must be at most 100 characters")
    @Schema(description = "middleName name", example = "Ivanovich")
    private String middleName;

//    @Column(name = "birth_date", nullable = false)
//    private Date birthDate;

    @NotNull(message = "birthDate is required")
    @Schema(description = "Birth date", example = "1950-01-01")
    // @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @Size(max = 255, message = "document must be at most 255 characters")
    @Schema(description = "document name", example = "Passport")
    private String document;

    @Size(max = 255, message = "address must be at most 255 characters")
    @Schema(description = "Residential address", example = "Minsk, Nezalegnosty str., 1")
    private String address;

    @Size(max = 20, message = "Phone must be at most 20 characters")
    @Schema(description = "Phone", example = "+375441001100")
    private String phone;

    @Size(max = 255, message = "email must be at most 255 characters")
    @Schema(description = "email", example = "test@mail.ru")
    @Email
    private String email;
}
