package com.edme.issuingBank.repositories;

import com.edme.issuingBank.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Modifying
    @Transactional
    @Query(value="DROP TABLE IF EXISTS issuingbankschema.card CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Transactional
    @Query(value="CREATE TABLE IF NOT EXISTS issuingbankschema.card(" +
                 "    id                              bigserial primary key," +
                 "    card_number                     varchar(50) UNIQUE not null," +
                 "    expiration_date                 date," +
                 "    holder_name                     varchar(50)        not null," +
                 "    card_status_id                  bigint REFERENCES issuingbankschema.card_status (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                 "    payment_system_id               bigint REFERENCES issuingbankschema.payment_system (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                 "    account_id                      bigint REFERENCES issuingbankschema.account (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                 "    client_id                       bigint REFERENCES issuingbankschema.client (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                 "    sent_to_processing_center       date," +
                 "    received_from_processing_center date)", nativeQuery = true)
    void createTable();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO issuingbankschema.card(card_number, expiration_date, holder_name, card_status_id, payment_system_id, " +
                   "account_id, client_id, sent_to_processing_center, received_from_processing_center)" +
                   "VALUES ('4123450000000019', '2025-12-31', 'IVAN I. IVANOV', 2, 1, 1, 1, '2022-10-21 12:10:33.695'," +
                   "        '2022-10-21 13:12:12.159')," +
                   "       ('5123450000000024', '2025-12-31', 'SEMION E. PETROV ', 3, 2, 2, 2, '2022-04-05 11:58:45.690'," +
                   "        '2022-04-05 12:59:54.199')," +
                   "       ('3123450000000037', '2025-10-31', 'DMITRY S. SIDOROV', 2, 3, 3, 3, '2022-10-20 12:22:32.234'," +
                   "        '2022-10-20 13:42:22.143')," +
                   "       ('4123450000000043', '2025-12-31', 'IVAN I. IVANOV', 2, 1, 4, 1, '2022-10-21 12:19:19.143'," +
                   "        '2022-10-21 13:24:39.648')", nativeQuery = true)
    void insertDefaultValues();
}
