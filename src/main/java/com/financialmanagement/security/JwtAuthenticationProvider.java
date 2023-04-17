package com.financialmanagement.security;

import com.financialmanagement.repository.CustomerRepository;
import com.financialmanagement.security.jwt.JwtAuthentication;
import com.financialmanagement.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomerRepository customerRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        String sessionId = jwtTokenProvider.getSubject(jwtAuthentication.getToken());


        if (jwtTokenProvider.isTokenValid(jwtAuthentication.getToken())) {
            String userName = customerRepository.findCustomerBySession(UUID.fromString(sessionId)).get().getEmail();
            jwtAuthentication.setSubject(sessionId);
            jwtAuthentication.setUsername(userName);
            return jwtAuthentication;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthentication.class);
    }
}
