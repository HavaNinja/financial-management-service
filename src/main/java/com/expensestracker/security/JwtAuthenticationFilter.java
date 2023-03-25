package com.expensestracker.security;

import com.expensestracker.exception.ServiceLayerException;
import com.expensestracker.repository.CustomerRepository;
import com.expensestracker.security.jwt.JwtAuthentication;
import com.expensestracker.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static com.expensestracker.utils.Constants.AUTHORIZATION_HEADER;
import static com.expensestracker.utils.Constants.TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerRepository customerRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authorizationHeader.substring(TOKEN_PREFIX.length());

        if (!jwtTokenProvider.isTokenValid(token)) {
            throw new ServiceLayerException("Invalid token", HttpStatus.FORBIDDEN);
        }

        final String sessionId = jwtTokenProvider.getSubject(token);
        String username = customerRepository.findBySessionsId(UUID.fromString(sessionId)).getEmail();

        JwtAuthentication jwtAuthentication = new JwtAuthentication(username, sessionId);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(jwtAuthentication);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }
}
