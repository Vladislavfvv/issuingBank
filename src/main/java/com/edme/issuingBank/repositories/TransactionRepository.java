package com.edme.issuingBank.repositories;

import com.edme.issuingBank.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS issuingbankschema.transaction CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Transactional
    @Query(value="CREATE TABLE IF NOT EXISTS issuingbankschema.transaction(" +
                 "    id                              bigserial primary key," +
                 "    transaction_date                date        not null," +
                 "    sum                             decimal," +
                 "    transaction_name                varchar(2550) not null," +
                 "    transaction_type_id             bigint REFERENCES issuingbankschema.transaction_type (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                 "    account_id                      bigint REFERENCES issuingbankschema.account (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                 "    sent_to_processing_center       timestamp," +
                 "    received_from_processing_center timestamp)", nativeQuery = true)
    void createTable();

    @Modifying
    @Transactional
    @Query(value="INSERT INTO issuingbankschema.transaction(transaction_date, sum, transaction_name, transaction_type_id, account_id," +
                 "                                          sent_to_processing_center, received_from_processing_center)" +
                 "VALUES ('2022-10-22', 1000.11, 'Cash deposit', 1, 2, '2022-10-22', '2022-10-22')," +
                 "       ('2022-04-06', 50000.92, 'Cash deposit', 2, 2, '2022-04-06', '2022-04-06')," +
                 "       ('2022-10-21', 750000.12, 'Cash deposit', 3, 2, '2022-10-21', '2022-10-21')," +
                 "       ('2022-10-23', 350.41, 'Money transfer', 1, 1, '2022-10-23', '2022-10-23')," +
                 "       ('2022-06-23', 1298.85, 'Commission', 2, 1, '2022-06-23', '2022-06-23')," +
                 "       ('2022-10-22', 35000.11, 'Payment of the invoice', 3, 1, '2022-10-22', '2022-10-22')," +
                 "       ('2022-10-22', 10000.0, 'Cash deposit', 4, 2, '2022-10-22', '2022-10-22')", nativeQuery=true)
    void insertDefaultValues();
}
