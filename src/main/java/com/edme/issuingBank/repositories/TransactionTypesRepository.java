package com.edme.issuingBank.repositories;

import com.edme.issuingBank.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionTypesRepository extends JpaRepository<TransactionType, Long> {

    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS issuingbankschema.transaction_type CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS issuingbankschema.transaction_type(" +
                   "    id                    bigserial primary key," +
                   "    transaction_type_name varchar(255) UNIQUE not null)", nativeQuery=true)
    void createTable();

    @Modifying
    @Transactional
    @Query(value="INSERT INTO issuingbankschema.transaction_type(transaction_type_name)" +
                 "VALUES ('Debit')," +
                 "       ('Credit')", nativeQuery=true)
    void insertDefaultValues();
}
