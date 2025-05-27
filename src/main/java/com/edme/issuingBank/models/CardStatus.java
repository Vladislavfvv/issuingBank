package com.edme.issuingBank.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "card_status", schema = "issuingbankschema")
public class CardStatus {//Определяет текущее состояние карты.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Уникальный идентификатор статуса карты.

    @Column(name = "card_status_name", length = 255, nullable = false, unique = true)
    private String cardStatusName; //Название статуса карты (например, "Активна", "Заблокирована").
}
