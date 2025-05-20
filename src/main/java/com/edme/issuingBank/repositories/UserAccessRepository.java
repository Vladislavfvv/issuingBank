package com.edme.issuingBank.repositories;


import com.edme.issuingBank.models.UserAccess;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess, Long> {
    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS issuingbankschema.user_access CASCADE", nativeQuery = true)
    int dropTable();

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS issuingbankschema.user_access (" +
                   "    id bigserial primary key," +
                   "    user_login varchar(255) UNIQUE not null," +
                   "    user_password varchar(255) not null," +
                   "    full_name varchar(255) not null," +
                   "    user_role varchar(255) not null)", nativeQuery = true)
    void createTable();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO issuingbankschema.user_access(user_login, user_password, full_name, user_role) VALUES " +
                   "('administrator', '111', 'Administrators full name', 'ADMIN')," +
                   "('manager', '222', 'Managers full name', 'USER') ON CONFLICT (user_login) DO NOTHING", nativeQuery = true)
    void insertDefaultValues();

    Optional<UserAccess> findByUserLogin(@NotBlank(message = "userLogin is required") @Size(max = 255, message = "userLogin must be at most 255 characters") String userLogin);
}
