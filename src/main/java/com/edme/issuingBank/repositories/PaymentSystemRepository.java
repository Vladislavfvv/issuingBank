package com.edme.issuingBank.repositories;

import com.edme.issuingBank.models.PaymentSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentSystemRepository extends JpaRepository<PaymentSystem, Long> {

    @Modifying
    @Transactional
    @Query(value="DROP TABLE IF EXISTS issuingbankschema.payment_system CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Transactional
    @Query(value="CREATE TABLE IF NOT EXISTS issuingbankschema.payment_system(" +
                 "    id                  bigserial primary key," +
                 "    payment_system_name varchar(50) UNIQUE not null," +
                 "    first_digit_bin     varchar(1)         not null)", nativeQuery = true)
    void createTable();

    @Modifying
    @Transactional
    @Query(value="INSERT INTO issuingbankschema.payment_system(payment_system_name, first_digit_bin)" +
                 "VALUES ('VISA International Service Association', '4')," +
                 "       ('Mastercard', '5')," +
                 "       ('JCB', '3')," +
                 "       ('American Express', '3')," +
                 "       ('Diners Club International', '3')," +
                 "       ('China UnionPay', '6')", nativeQuery = true)
    void insertDefaultValues();

}
