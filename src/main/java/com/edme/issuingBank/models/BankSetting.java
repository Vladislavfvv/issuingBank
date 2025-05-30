package com.edme.issuingBank.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "bank_settings", schema = "issuingbankschema")
public class BankSetting { //Назначение: Хранит параметры настройки банка и их текущее состояние.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// Уникальный идентификатор настройки банка.

    @Column(name="setting", length = 100, nullable = false, unique = true)
    private String setting;//Название настройки.

    @Column(name="current_value", length = 255, nullable = false)
    private String currentValue;// Текущее значение настройки.

    @Column(name="description", length = 255, nullable = false)
    private String description; //Описание настройки.
}
