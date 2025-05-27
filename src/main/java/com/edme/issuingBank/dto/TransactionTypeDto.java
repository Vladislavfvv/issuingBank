package com.edme.issuingBank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionTypeDto {
    @Schema(description = "Unique identifier of the transactionType", example = "123")
    private Long id;

    @NotNull(message = "transactionTypeName is required")
    @Size(max = 50, message = "transactionTypeName must be at most 255 characters")
    @Schema(description = "transactionTypeName name", example = "Перевод")
    private String transactionTypeName; //Название типа транзакции (например, "Перевод", "Снятие наличных").
}
