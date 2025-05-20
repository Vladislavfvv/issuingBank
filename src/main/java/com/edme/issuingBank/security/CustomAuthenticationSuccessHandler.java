package com.edme.issuingBank.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess (
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // String redirectUrl = "/default";
        String redirectUrl = "/swagger-ui/index.html";

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN")) {
                redirectUrl = "/swagger-ui/index.html#/admin";
                //redirectUrl = "/admin/welcome";
                break;
            } else if (role.equals("ROLE_USER")) {
                //redirectUrl = "/user/welcome";
                redirectUrl = "/swagger-ui/index.html#/user";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}
