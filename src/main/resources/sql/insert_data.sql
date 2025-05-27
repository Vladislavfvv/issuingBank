INSERT INTO issuingbankschema.bank_setting(setting, current_value, description)
VALUES ('license', 'Генеральная лицензия Банка России на осуществление банковских операций №____ от _______ г. ',
        'Лицензия'),
       ('full_name', 'Публичное акционерное общество «Банк-эмитент номер один»', 'Полное наименование банка'),
       ('abbreviated_name', 'ПАО Банк-эмитент №1', 'Сокращенное наименование банка'),
       ('registration_address', '________, _____, , ул., д.', 'Юридический адрес'),
       ('postal_address', '________, _____, , ул., д.', 'Почтовый адрес '),
       ('correspondent_account', '30101810____________', 'Корреспондентский счет'),
       ('bic', '________', 'Отделение Центрального банка'),
       ('bin', '041234569', 'Банковский идентификационный код (БИК)'),
       ('code_1', '12345', 'Идентификационный номер эмитента (БИН карты)'),
       ('code_2', '123456789', 'КПП'),
       ('code_3', '123456789', 'ИНН'),
       ('code_4', '1234567890123', 'ОГРН'),
       ('code_5', '12345678', 'ОКПО'),
       ('code_6', '123456789', 'ОКТМО'),
       ('code_7', '1234', 'ОКВЭД'),
       ('code_8', '1234567', 'ОКОГУ'),
       ('code_9', '12', 'ОКФС'),
       ('code_10', '12345', 'ОКОПФ'),
       ('swift', '123456789', 'SWIFT-код'),
       ('e_mail', '123@456.com', 'E-mail'),
       ('www', 'www.123.com', 'Сайт'),
       ('registration_date', '________', 'Дата внесения в ЕГРЮЛ');

INSERT INTO issuingbankschema.card_status(card_status_name)
VALUES ('Card is not active'),
       ('Card is valid'),
       ('Card is temporarily blocked'),
       ('Card is lost'),
       ('Card is compromised');

INSERT INTO issuingbankschema.payment_system(payment_system_name, first_digit_bin)
VALUES ('VISA International Service Association', '4'),
       ('Mastercard', '5'),
       ('JCB', '3'),
       ('American Express', '3'),
       ('Diners Club International', '3'),
       ('China UnionPay', '6');

INSERT INTO issuingbankschema.account_type(account_type_name)
VALUES ('Активный'),
       ('Пассивный');

INSERT INTO issuingbankschema.currency(currency_digital_code, currency_letter_code, currency_digital_code_account,
                                       currency_name)
VALUES ('643', 'RUB', '810', 'Russian Ruble'),
 ('980', 'UAN', '980', 'Hryvnia'),
 ('840', 'USD', '840', 'US Dollar'),
 ('978', 'EUR', '978', 'Euro'),
 ('392', 'JPY', '392', 'Yen'),
 ('156', 'CNY', '156', 'Yuan Renminbi'),
 ('826', 'GBP', '826', 'Pound Sterling');

INSERT INTO issuingbankschema.transaction_type(transaction_type_name)
VALUES ('Debit'),
       ('Credit');

INSERT INTO issuingbankschema.client(last_name, first_name, middle_name, birth_date, document, address, phone, email)
VALUES ('Иванов', 'Иван', 'Иванович', '1980-01-30', '1234 123456 выдан 01.01.2000',
        'Москва, ул. Шаболовская, 37 кв. 20', '+79221234567', 'ivanivanov@list.ru'),
       ('Петров', 'Семен', 'Егорович', '1989-12-31', '5678 789012 выдан 20.10.2012',
        'Санкт Петербург, Лиговский  пр-т, 96 кв. 15', '+79121234568', 'petrov@list.ru'),
       ('Сидоров', 'Дмитрий', 'Степанович', '1976-10-20', '9012 345678 выдан 16.07.2018',
        'Тюмень, ул. Республики, 21 кв.12', '+79041234569', 'dmsydorov@mail.ru');

INSERT INTO issuingbankschema.account(account_number, balance, currency, accountType, client, accountOpeningDate,
                                      suspendingOperations, accountClosingDate)
VALUES ('40817810800000000001', '10.7', 1, 2, 1, '2022-10-21', false, 'null'),
       ('40817810800000000002', '48.07', 1, 2, 2, '2022-04-05', false, 'null'),
       ('40817810800000000003', '71.01', 1, 2, 3, '2022-10-20', false, 'null'),
       ('40817810800000000004', '10.02', 3, 2, 1, '2022-10-21', false, 'null');

INSERT INTO issuingbankschema.card(card_number, expiration_date, holder_name, card_status_id, payment_system_id,
                                   account_id, client_id, sent_to_processing_center, received_from_processing_center)
VALUES ('4123450000000019', '2025-12-31', 'IVAN I. IVANOV', 2, 1, 1, 1, '2022-10-21 12:10:33.695',
        '2022-10-21 13:12:12.159'),
       ('5123450000000024', '2025-12-31', 'SEMION E. PETROV ', 3, 2, 2, 2, '2022-04-05 11:58:45.690',
        '2022-04-05 12:59:54.199'),
       ('3123450000000037', '2025-10-31', 'DMITRY S. SIDOROV', 2, 3, 3, 3, '2022-10-20 12:22:32.234',
        '2022-10-20 13:42:22.143'),
       ('4123450000000043', '2025-12-31', 'IVAN I. IVANOV', 2, 1, 4, 1, '2022-10-21 12:19:19.143',
        '2022-10-21 13:24:39.648');

INSERT INTO issuingbankschema.transaction(transaction_date, sum, transaction_name, transaction_type_id, account_id,
                                          sent_to_processing_center, received_from_processing_center)
VALUES ('2022-10-22', 1000.11, 'Cash deposit', 1, 2, '2022-10-22', '2022-10-22'),
       ('2022-04-06', 50000.92, 'Cash deposit', 2, 2, '2022-04-06', '2022-04-06'),
       ('2022-10-21', 750000.12, 'Cash deposit', 3, 2, '2022-10-21', '2022-10-21'),
       ('2022-10-23', 350.41, 'Money transfer', 1, 1, '2022-10-23', '2022-10-23'),
       ('2022-06-23', 1298.85, 'Commission', 2, 1, '2022-06-23', '2022-06-23'),
       ('2022-10-22', 35000.11, 'Payment of the invoice', 3, 1, '2022-10-22', '2022-10-22'),
       ('2022-10-22', 10000.0, 'Cash deposit', 4, 2, '2022-10-22', '2022-10-22');


