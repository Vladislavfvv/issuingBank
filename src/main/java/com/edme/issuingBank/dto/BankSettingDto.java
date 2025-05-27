package com.edme.issuingBank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankSettingDto {
    @Schema(description = "Unique account identifier", example = "123")
    private Long id;

    @NotBlank(message = "setting is required")
    @Size(max = 50, message = "setting must be at most 10 characters")
    private String setting;

    @NotBlank(message = "currentValue name is required")
    @Size(max = 255, message = "currentValue name must be at most 255 characters")
    private String currentValue;

    @NotBlank(message = "description name is required")
    @Size(max = 255, message = "description name must be at most 255 characters")
    private String description;
}
