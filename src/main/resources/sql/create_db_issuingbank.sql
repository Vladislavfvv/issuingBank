--
-- CREATE DATABASE issuingbank;
-- -- переключиться на БД
-- \c issuingbank;
-- CREATE SCHEMA IF NOT EXISTS issuingbankschema;
-- -- установка схемы по умолчанию
-- SET search_path TO issuingbankschema;

CREATE TABLE IF NOT EXISTS issuingbankschema.bank_settings
(
    id            bigserial primary key,
    setting       varchar(100) UNIQUE not null,
    current_value varchar(255)        not null,
    description   varchar(255)        not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.card_statuses
(
    id               bigserial primary key,
    card_status_name varchar(255) UNIQUE not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.payment_systems
(
    id                  bigserial primary key,
    payment_system_name varchar(50) UNIQUE not null,
    first_digit_bin     varchar(1)         not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.account_types
(
    id                bigserial primary key,
    account_type_name varchar(255) UNIQUE not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.currencies
(
    id                            bigserial primary key,
    currency_digital_code         varchar(3)          not null,
    currency_letter_code          varchar(3)          not null,
    currency_digital_code_account varchar(3)          not null,
    currency_name                 varchar(255) UNIQUE not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.transaction_types
(
    id                    bigserial primary key,
    transaction_type_name varchar(255) UNIQUE not null
);

CREATE TABLE IF NOT EXISTS issuingbankschema.clients
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

CREATE TABLE IF NOT EXISTS issuingbankschema.accounts
(
    id                   bigserial primary key,
    account_number       varchar(50) UNIQUE not null,
    balance              decimal,
    currency             bigint REFERENCES issuingbankschema.currencies (id) ON DELETE CASCADE ON UPDATE CASCADE,
    accountType          bigint REFERENCES issuingbankschema.account_types (id) ON DELETE CASCADE ON UPDATE CASCADE,
    client               bigint REFERENCES issuingbankschema.clients (id) ON DELETE CASCADE ON UPDATE CASCADE,
    accountOpeningDate   date               not null,
    suspendingOperations boolean,
    accountClosingDate   date
);

CREATE TABLE IF NOT EXISTS issuingbankschema.cards
(
    id                              bigserial primary key,
    card_number                     varchar(50) UNIQUE not null,
    expiration_date                 date,
    holder_name                     varchar(50)        not null,
    card_status_id                  bigint REFERENCES issuingbankschema.card_statuses (id) ON DELETE CASCADE ON UPDATE CASCADE,
    payment_system_id               bigint REFERENCES issuingbankschema.payment_systems (id) ON DELETE CASCADE ON UPDATE CASCADE,
    account_id                      bigint REFERENCES issuingbankschema.accounts (id) ON DELETE CASCADE ON UPDATE CASCADE,
    client_id                       bigint REFERENCES issuingbankschema.clients (id) ON DELETE CASCADE ON UPDATE CASCADE,
    sent_to_processing_center       date,
    received_from_processing_center date
);

CREATE TABLE IF NOT EXISTS issuingbankschema.transactions
(
    id                              bigserial primary key,
    transaction_date                date        not null,
    sum                             decimal,
    transaction_name                varchar(2550) not null,
    transaction_type_id             bigint REFERENCES issuingbankschema.transaction_types (id) ON DELETE CASCADE ON UPDATE CASCADE,
    account_id                      bigint REFERENCES issuingbankschema.accounts (id) ON DELETE CASCADE ON UPDATE CASCADE,
    sent_to_processing_center       timestamp,
    received_from_processing_center timestamp
);