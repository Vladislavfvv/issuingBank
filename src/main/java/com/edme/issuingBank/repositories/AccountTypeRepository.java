package com.edme.issuingBank.repositories;

import com.edme.issuingBank.models.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    @Modifying
    @Transactional
    @Query(value="DROP TABLE IF EXISTS issuingbankschema.account_type CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Transactional
    @Query(value="CREATE TABLE IF NOT EXISTS issuingbankschema.account_type(" +
                 "    id                bigserial primary key," +
                 "    account_type_name varchar(255) UNIQUE not null)", nativeQuery = true)
    void createTable();

    @Modifying
    @Transactional
    @Query(value="INSERT INTO issuingbankschema.account_type(account_type_name)\n" +
                 "VALUES ('Активный'),\n" +
                 "       ('Пассивный')", nativeQuery = true)
    void insertDefaultValues();
}
