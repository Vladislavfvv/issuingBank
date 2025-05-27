package com.edme.issuingBank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDto {

    @Schema(description = "Unique identifier of the transaction", example = "123")
    private Long id;

    @NotNull(message = "transactionDate is required")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Date when the transaction occurred", example = "21/05/2025")
    private Date transactionDate;

    @NotNull(message = "sum is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "sum must be greater than 0") //inclusive-  стого больше 0,0

    @Schema(description = "Amount of the transaction", example = "1500.75")
    private BigDecimal sum;

    @NotBlank(message = "transactionName is required")
    @Schema(description = "Name or description of the transaction", example = "Transfer to savings account")
    private String transactionName;

    @NotNull(message = "transactionTypeId is required")
    private TransactionTypeDto transactionTypeId;

    @NotNull(message = "accountId is required")
    @Schema(description = "Reference to the related account", example = "5")
    private AccountDto accountId;

    @NotNull(message = "sentToProcessingCenter is required")
    @Schema(description = "Timestamp when transaction was sent to the processing center", example = "2025-05-21T10:15:30")
    private Timestamp sentToProcessingCenter;

    @NotNull(message = "receivedFromProcessingCenter is required")
    @Schema(description = "Timestamp when response was received from the processing center", example = "2025-05-21T10:16:30")
    private Timestamp receivedFromProcessingCenter;
}
