package com.edme.issuingBank.repositories;

import com.edme.issuingBank.models.BankSetting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BankSettingRepository extends JpaRepository<BankSetting, Long> {

    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS issuingbankschema.bank_setting CASCADE", nativeQuery = true)
    void dropTable();


    @Modifying
    @Transactional
    @Query(value="CREATE TABLE IF NOT EXISTS issuingbankschema.bank_setting(" +
                 "    id            bigserial primary key," +
                 "    setting       varchar(100) UNIQUE not null," +
                 "    current_value varchar(255)        not null," +
                 "    description   varchar(255)        not null" +
                 ")", nativeQuery = true )
    void createTable();

    @Modifying
    @Transactional
    @Query(value="INSERT INTO issuingbankschema.bank_setting(setting, current_value, description)" +
                 "VALUES ('license', 'Генеральная лицензия Банка России на осуществление банковских операций №____ от _______ г. ', 'Лицензия')," +
                 "       ('full_name', 'Публичное акционерное общество «Банк-эмитент номер один»', 'Полное наименование банка')," +
                 "       ('abbreviated_name', 'ПАО Банк-эмитент №1', 'Сокращенное наименование банка')," +
                 "       ('registration_address', '________, _____, , ул., д.', 'Юридический адрес')," +
                 "       ('postal_address', '________, _____, , ул., д.', 'Почтовый адрес ')," +
                 "       ('correspondent_account', '30101810____________', 'Корреспондентский счет')," +
                 "       ('bic', '________', 'Отделение Центрального банка')," +
                 "       ('bin', '041234569', 'Банковский идентификационный код (БИК)')," +
                 "       ('code_1', '12345', 'Идентификационный номер эмитента (БИН карты)')," +
                 "       ('code_2', '123456789', 'КПП')," +
                 "       ('code_3', '123456789', 'ИНН')," +
                 "       ('code_4', '1234567890123', 'ОГРН')," +
                 "       ('code_5', '12345678', 'ОКПО')," +
                 "       ('code_6', '123456789', 'ОКТМО')," +
                 "       ('code_7', '1234', 'ОКВЭД')," +
                 "       ('code_8', '1234567', 'ОКОГУ')," +
                 "       ('code_9', '12', 'ОКФС')," +
                 "       ('code_10', '12345', 'ОКОПФ')," +
                 "       ('swift', '123456789', 'SWIFT-код')," +
                 "       ('e_mail', '123@456.com', 'E-mail')," +
                 "       ('www', 'www.123.com', 'Сайт')," +
                 "       ('registration_date', '________', 'Дата внесения в ЕГРЮЛ')", nativeQuery = true)
    void insertDefaultValues();


    Optional<BankSetting> findBySetting(@NotBlank(message = "setting is required") @Size(max = 50, message = "setting must be at most 10 characters") String setting);
}

