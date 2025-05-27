package com.edme.issuingBank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDto {
    @Schema(description = "Unique account identifier", example = "123")
    private Long id;

    @NotNull(message = "Account number is required")
    @Pattern(regexp = "^\\d{20}$", message = "Account number must be exactly 20 digits")
    @Schema(description = "Account number (exactly 20 digits)", example = "40817810800000000001")
    private String accountNumber;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.00", message = "Balance cannot be negative")
    @Digits(integer = 12, fraction = 2, message = "Balance must be in the format of up to 12 digits and 2 decimal places")
    @Schema(description = "Account balance", example = "1000.50")
    private BigDecimal balance;

    @NotNull(message = "Currency ID is required")
    @Schema(description = "Currency identifier", example = "1")
    private CurrencyDto currencyId;

    @NotNull(message = "Account type ID is required")
    @Schema(description = "Account type identifier", example = "2")
    private AccountTypeDto accountTypeId;

    @NotNull(message = "Client ID is required")
    @Schema(description = "Client identifier", example = "10")
    private ClientDto clientId;

    @PastOrPresent(message = "Account opening date cannot be in the future")
    @NotNull(message = "Account opening date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Date the account was opened", example = "2022-01-15")
    private LocalDate accountOpeningDate;

    @Schema(description = "Whether operations are suspended on the account", example = "false")
    private boolean suspendingOperations;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Date the account was closed", example = "2023-12-01")
    private LocalDate accountClosingDate;
}
