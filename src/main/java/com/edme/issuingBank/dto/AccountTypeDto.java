package com.edme.issuingBank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountTypeDto {
    @Schema(description = "Unique account identifier", example = "123")
    private Long id;

    @NotNull(message = "accountTypeName is required")
    @Schema(description = "accountTypeName identifier", example = "Дебет")
    private String accountTypeName;
}
