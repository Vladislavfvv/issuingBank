package com.edme.issuingBank.security;

import com.edme.issuingBank.models.UserAccess;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
//UserDetailsImpl (адаптер пользователя)
//UserDetails это интерфейс Spring Security, который представляет пользователя в системе безопасности
//Преобразует роль из UserAccess в формат Spring Security (с префиксом ROLE_)
//Предоставляет методы для проверки состояния учетной записи (в вашем случае всегда true)
@AllArgsConstructor// Автоматически генерирует конструктор со всеми полями (Lombok)
public class UserDetailsImpl implements UserDetails {

    private final UserAccess user;

    // Преобразуем роль пользователя в формат Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().toUpperCase()));
    }

    //Spring Security автоматически добавляет префикс ROLE_, поэтому hasRole('ADMIN') — это проверка на "ROLE_ADMIN"
    //для проверки при аутентификации
    @Override
    public String getPassword() {
        return user.getUserPassword();
    }
//идентификатор при аутентификации
    @Override
    public String getUsername() {
        return user.getUserLogin();
    }
//Состояние учетной записи:
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
