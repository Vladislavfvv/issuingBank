package com.edme.issuingBank.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "payment_systems", schema = "issuingbankschema")
public class PaymentSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="payment_system_name", length = 50, nullable = false, unique = true)
    private String paymentSystemName;//Название платёжной системы (например, Visa, MasterCard).

    @Column(name="first_digit_bin", length = 1, nullable = false)
    private String firstDigitBin; //Первый символ BIN для идентификации платёжной системы.
}
