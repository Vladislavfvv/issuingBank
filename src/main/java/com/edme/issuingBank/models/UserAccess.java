package com.edme.issuingBank.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user_accesses", schema = "issuingbankschema")
public class UserAccess { //Назначение: Хранит информацию о пользователях системы и их ролях.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_login", length = 255, nullable = false)
    private String userLogin;

    @Column(name = "user_password", length = 255, nullable = false)
    private String userPassword;

    @Column(name = "full_name", length = 255, nullable = false)
    private String fullName;

    @Column(name = "user_role", length = 255, nullable = false)
    private String userRole;
}


