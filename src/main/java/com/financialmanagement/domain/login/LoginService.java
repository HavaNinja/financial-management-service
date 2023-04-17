package com.financialmanagement.domain.login;

import com.financialmanagement.domain.login.dto.LoginRequest;
import com.financialmanagement.domain.login.dto.LoginResponse;
import com.financialmanagement.domain.login.entity.Session;
import com.financialmanagement.utils.exception.ServiceLayerException;
import com.financialmanagement.auth.CustomerDetails;
import com.financialmanagement.auth.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.financialmanagement.utils.Constants.TOKEN_PREFIX;

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
            throw new ServiceLayerException("Username Or password is invalid", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Session session = Session.builder().customer(customerDetails.getCustomer()).active(true).build();

        UUID sessionId = sessionRepository.save(session).getId();

        String jwtToken = jwtTokenProvider.generateJwtToken(sessionId.toString());

        return new LoginResponse(TOKEN_PREFIX + jwtToken);

    }

    @Transactional
    public void logOut(UUID sessionId) {
        sessionRepository.invalidateSession(sessionId);
    }
}
