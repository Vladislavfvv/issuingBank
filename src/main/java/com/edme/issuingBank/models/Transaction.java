package com.edme.issuingBank.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="transaction", schema="issuingbankschema")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="transaction_date", nullable=false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date transactionDate; //Дата совершения операции.

    @Column(name="sum", nullable=false)
    private BigDecimal sum;//Сумма операции .

    @Column(name="transaction_name", nullable=false)
    private String transactionName; //Название транзакции.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType  transactionType; //Ссылка на тип транзакции.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;//Ссылка на счёт.

    @Column(name="sent_to_processing_center", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Timestamp sentToProcessingCenter; //Время отправки в     процессинговый центр.

    @Column(name="received_from_processing_center", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Timestamp receivedFromProcessingCenter;//Время получения     данных из процессингового центра.
}
