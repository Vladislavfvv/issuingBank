package com.edme.issuingBank.repositories;

import com.edme.issuingBank.models.Client;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Modifying
    @Transactional
    @Query(value="DROP TABLE IF EXISTS issuingbankschema.client CASCADE", nativeQuery = true)
    void dropTable();


    @Modifying
    @Transactional
    @Query(value="CREATE TABLE IF NOT EXISTS issuingbankschema.client(" +
                 "    id          bigserial primary key," +
                 "    last_name   varchar(100)        not null," +
                 "    first_name  varchar(100)        not null," +
                 "    middle_name varchar(100)        not null," +
                 "    birth_date  date                not null," +
                 "    document    varchar(255)        not null," +
                 "    address     varchar(255)        not null," +
                 "    phone       varchar(20)         not null," +
                 "    email       varchar(255) UNIQUE not null)", nativeQuery=true)
    int createTable();

    @Modifying
    @Transactional
    @Query(value="INSERT INTO issuingbankschema.client(last_name, first_name, middle_name, birth_date, document, address, phone, email)" +
                 "VALUES ('Иванов', 'Иван', 'Иванович', '1980-01-30', '1234 123456 выдан 01.01.2000'," +
                 "        'Москва, ул. Шаболовская, 37 кв. 20', '+79221234567', 'ivanivanov@list.ru')," +
                 "       ('Петров', 'Семен', 'Егорович', '1989-12-31', '5678 789012 выдан 20.10.2012'," +
                 "        'Санкт Петербург, Лиговский  пр-т, 96 кв. 15', '+79121234568', 'petrov@list.ru')," +
                 "       ('Сидоров', 'Дмитрий', 'Степанович', '1976-10-20', '9012 345678 выдан 16.07.2018'," +
                 "        'Тюмень, ул. Республики, 21 кв.12', '+79041234569', 'dmsydorov@mail.ru')", nativeQuery = true)
    int insertDefaultValues();

    Optional<Client> findByDocument(@Size(max = 255, message = "document must be at most 255 characters") String document);
}
