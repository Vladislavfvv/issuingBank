package com.edme.issuingBank.security;

import com.edme.issuingBank.mappers.UserAccessMapper;
import com.edme.issuingBank.models.UserAccess;
import com.edme.issuingBank.repositories.UserAccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


//Аутентификация через БД
//UserDetailsService реализацией UserDetailsServiceImpl загружает данные пользователя
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserAccessRepository userAccessRepository;
    private final UserAccessMapper userAccessMapper;
    private final PasswordEncoder passwordEncoder;

  //Находит пользователя по логину
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccess userAccess = userAccessRepository.findByUserLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new UserDetailsImpl(userAccess); // Преобразование UserAccess в UserDetails
    }
}
