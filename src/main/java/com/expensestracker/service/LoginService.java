package com.expensestracker.service;

import com.expensestracker.dto.LoginRequest;
import com.expensestracker.dto.LoginResponse;
import com.expensestracker.entity.Session;
import com.expensestracker.repository.SessionRepository;
import com.expensestracker.security.CustomerDetails;
import com.expensestracker.security.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.expensestracker.utils.Constants.TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final SessionRepository sessionRepository;


    public LoginResponse login(LoginRequest loginRequest) {
        CustomerDetails customerDetails = (CustomerDetails) userDetailsService.loadUserByUsername(loginRequest.getUsername());

        if (!passwordEncoder.matches(loginRequest.getPassword(), customerDetails.getPassword())) {
            throw new BadCredentialsException("Username Or password is invalid");
        }

        Session session = Session.builder()
                .customer(customerDetails.getCustomer())
                .active(true)
                .build();

        UUID sessionId = sessionRepository.save(session).getId();

        String jwtToken = jwtTokenProvider.generateJwtToken(sessionId.toString());

        return new LoginResponse(TOKEN_PREFIX + jwtToken);

    }

    @Transactional
    public void logOut(UUID sessionId) {
        sessionRepository.invalidateSession(sessionId);
    }
}
