--
-- CREATE DATABASE issuingbank;
-- -- переключиться на БД
-- \c issuingbank;
-- CREATE SCHEMA IF NOT EXISTS issuingbankschema;
-- -- установка схемы по умолчанию
-- SET search_path TO issuingbankschema;

CREATE TABLE IF NOT EXISTS issuingbankschema.bank_setting
(
    id            bigserial primary key,
    setting       varchar(100) UNIQUE not null,
    current_value varchar(255)        not null,
    description   varchar(255)        not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.card_status
(
    id               bigserial primary key,
    card_status_name varchar(255) UNIQUE not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.payment_system
(
    id                  bigserial primary key,
    payment_system_name varchar(50) UNIQUE not null,
    first_digit_bin     varchar(1)         not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.account_type
(
    id                bigserial primary key,
    account_type_name varchar(255) UNIQUE not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.currency
(
    id                            bigserial primary key,
    currency_digital_code         varchar(3)          not null,
    currency_letter_code          varchar(3)          not null,
    currency_digital_code_account varchar(3)          not null,
    currency_name                 varchar(255) UNIQUE not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.transaction_type
(
    id                    bigserial primary key,
    transaction_type_name varchar(255) UNIQUE not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.client
(
    id          bigserial primary key,
    last_name   varchar(100)        not null,
    first_name  varchar(100)        not null,
    middle_name varchar(100)        not null,
    birth_date  date                not null,
    document    varchar(255)        not null,
    address     varchar(255)        not null,
    phone       varchar(20)         not null,
    email       varchar(255) UNIQUE not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.account
(
    id                   bigserial primary key,
    account_number       varchar(50) UNIQUE not null,
    balance              decimal,
    currency             bigint REFERENCES issuingbankschema.currency (id) ON DELETE CASCADE ON UPDATE CASCADE,
    accountType          bigint REFERENCES issuingbankschema.account_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
    client               bigint REFERENCES issuingbankschema.client (id) ON DELETE CASCADE ON UPDATE CASCADE,
    accountOpeningDate   date               not null,
    suspendingOperations boolean,
    accountClosingDate   date
);

CREATE TABLE IF NOT EXISTS issuingbankschema.card
(
    id                              bigserial primary key,
    card_number                     varchar(50) UNIQUE not null,
    expiration_date                 date,
    holder_name                     varchar(50)        not null,
    card_status_id                  bigint REFERENCES issuingbankschema.card_status (id) ON DELETE CASCADE ON UPDATE CASCADE,
    payment_system_id               bigint REFERENCES issuingbankschema.payment_system (id) ON DELETE CASCADE ON UPDATE CASCADE,
    account_id                      bigint REFERENCES issuingbankschema.account (id) ON DELETE CASCADE ON UPDATE CASCADE,
    client_id                       bigint REFERENCES issuingbankschema.client (id) ON DELETE CASCADE ON UPDATE CASCADE,
    sent_to_processing_center       date,
    received_from_processing_center date
);

CREATE TABLE IF NOT EXISTS issuingbankschema.transaction
(
    id                              bigserial primary key,
    transaction_date                date        not null,
    sum                             decimal,
    transaction_name                varchar(2550) not null,
    transaction_type_id             bigint REFERENCES issuingbankschema.transaction_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
    account_id                      bigint REFERENCES issuingbankschema.account (id) ON DELETE CASCADE ON UPDATE CASCADE,
    sent_to_processing_center       timestamp,
    received_from_processing_center timestamp

);