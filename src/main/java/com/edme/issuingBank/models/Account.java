package com.edme.issuingBank.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "accounts", schema = "issuingbankschema")
public class Account {//Назначение: Учитывает данные о счетах клиентов.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", length = 20, unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

//    @Column(name = "account_opening_date", nullable = false)
//    private Date accountOpeningDate;

    @Column(name = "account_opening_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date accountOpeningDate;

    @Column(name = "suspending_operations", nullable = false)
    private boolean suspendingOperations;

//    @Column(name = "account_clousing_date")
//    private Date accountClosingDate;
    @Column(name = "account_clousing_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date accountClosingDate;
}
