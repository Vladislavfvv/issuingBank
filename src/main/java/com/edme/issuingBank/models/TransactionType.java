package com.edme.issuingBank.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="transaction_type", schema = "issuingbankschema")
public class TransactionType {//Назначение: Определяет возможные виды банковских операций.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_type_name", nullable = false, length = 255, unique = true)
    private String transactionTypeName; //Название типа транзакции (например, "Перевод", "Снятие наличных").
}
