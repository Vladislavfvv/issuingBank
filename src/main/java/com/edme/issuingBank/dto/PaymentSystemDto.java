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
public class PaymentSystemDto {
    @Schema(description = "Unique identifier of the payment system", example = "1")
    private Long id;

    @NotBlank(message = "paymentSystemName is required")
    @Size(max = 50, message = "paymentSystemName must be at most 50 characters")
    @Schema(description = "Name of the payment system", example = "Visa")
    private String paymentSystemName;

    @NotBlank(message = "firstDigitBin is required")
    @Size(min = 1, max = 1, message = "firstDigitBin must be exactly 1 character")
    @Schema(description = "First digit of BIN used to identify the payment system", example = "4")
    private String firstDigitBin;
}
