package com.edme.issuingBank.repositories;

import com.edme.issuingBank.models.Account;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS issuingbankschema.account CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS issuingbankschema.account(" +
                   "    id bigserial primary key," +
                   "    account_number varchar(20) UNIQUE not null," +
                   "    balance decimal," +
                   "    currency bigint REFERENCES issuingbankschema.currency(id) ON DELETE CASCADE ON UPDATE CASCADE," +
                   "    accountType bigint REFERENCES issuingbankschema.account_type(id)  ON DELETE CASCADE ON UPDATE CASCADE," +
                   "    client bigint REFERENCES issuingbankschema.client(id) ON DELETE CASCADE ON UPDATE CASCADE," +
                   "    accountOpeningDate date not null," +
                   "    suspendingOperations boolean," +
                   "    accountClosingDate date)", nativeQuery = true)
    void createTable();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO issuingbankschema.account(account_number, balance, currency, accountType, client, accountOpeningDate, suspendingOperations, accountClosingDate)" +
                   "VALUES ('40817810800000000001', '10.7', 1, 2, 1, '2022-10-21', false, 'null')," +
                   "       ('40817810800000000002', '48.07', 1, 2, 2, '2022-04-05', false, 'null')," +
                   "       ('40817810800000000003', '71.01', 1, 2, 3, '2022-10-20', false, 'null')," +
                   "       ('40817810800000000004', '10.02', 3, 2, 1, '2022-10-21', false, 'null') ON CONFLICT (account_number) DO NOTHING", nativeQuery = true)
    void insertDefaultValues();


    Optional<Account> findByAccountNumber(@NotNull(message = "Account number is required") @Pattern(regexp = "^\\d{20}$", message = "Account number must be exactly 20 digits") String accountNumber);
}


