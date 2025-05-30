package com.edme.issuingBank.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "currencies", schema = "issuingbankschema")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="currency_digital_code", length = 3, nullable = false)
    private String currencyDigitalCode;//Цифровой код валюты (например, 840 для USD).

    @Column(name="currency_letter_code", length = 3, nullable = false)
    private String currencyLetterCode;//Буквенный код валюты (например, USD).

    @Column(name="currency_digital_code_account", length = 3, nullable = false)
    private String currencyDigitalCodeAccount;//Альтернативный цифровой код валюты для учёта.

    @Column(name="currency_name", length = 255, nullable = false, unique = true)
    private String currencyName;//Название валюты.
}


