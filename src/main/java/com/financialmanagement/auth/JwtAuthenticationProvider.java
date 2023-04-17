package com.financialmanagement.auth;

import com.financialmanagement.domain.customer.CustomerRepository;
import com.financialmanagement.auth.jwt.JwtAuthentication;
import com.financialmanagement.auth.jwt.JwtTokenProvider;
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
