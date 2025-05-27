package com.edme.issuingBank.models;

import com.edme.issuingBank.dto.PaymentSystemDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "card", schema = "issuingbankschema")
public class Card {//Назначение: Хранит сведения о выпущенных картах.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", length = 50, nullable = false, unique = true)
    private String cardNumber;//Номер карты.

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date expirationDate;//Срок действия карты.

    @Column(name = "holder_name", length = 50, nullable = false)
    private String holderName;//Имя держателя карты.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_status_id", nullable = false)
    private CardStatus cardStatus;// Ссылка на статус карты.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_system_id", nullable = false)
    private PaymentSystem paymentSystem;//Ссылка на платёжную систему.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;// Ссылка на счёт.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    //@Column(name = "client_id", nullable = false)
    private Client clientId;//Ссылка на клиента.

    @Column(name = "sent_to_processing_center", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp sentToProcessingCenter;//Дата отправки данных в     процессинговый центр.

    //    @Column(name="received_from_processing_center", nullable=false)
//    @Temporal(TemporalType.TIMESTAMP)

    //если с LocalDateTime:
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
//    @Schema(description = "Timestamp when data was received from processing center", example = "2022-10-21 13:12:12.159")
//    @Column(name = "received_from_processing_center", nullable = false)
    //private LocalDateTime receivedFromProcessingCenter;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Schema(description = "Timestamp when data was received from processing center", example = "2022-10-21 13:12:12.159")
    @Column(name = "received_from_processing_center", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp receivedFromProcessingCenter;//Дата получения данных     из процессингового центра.
}
