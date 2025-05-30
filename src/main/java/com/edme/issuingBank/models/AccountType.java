package com.edme.issuingBank.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "account_types", schema = "issuingbankschema")
public class AccountType {//Особенности проведения операций по Активным и Пассивным счетам
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// Уникальный идентификатор.

    @Column(name="account_type_name", length = 255, nullable = false, unique = true)
    private String accountTypeName;// Название типа счёта (например, "Активный", "Пассивный").
}
