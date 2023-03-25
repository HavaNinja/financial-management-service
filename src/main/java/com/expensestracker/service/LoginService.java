package com.expensestracker.service;

import com.expensestracker.dto.LoginRequest;
import com.expensestracker.dto.LoginResponse;
import com.expensestracker.entity.Customer;
import com.expensestracker.entity.Session;
import com.expensestracker.exception.ServiceLayerException;
import com.expensestracker.repository.CustomerRepository;
import com.expensestracker.repository.SessionRepository;
import com.expensestracker.security.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.expensestracker.utils.Constants.TOKEN_PREFIX;

@Service
public class LoginService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SessionRepository sessionRepository;


    public LoginResponse login(LoginRequest loginRequest) {
        Optional<Customer> customer = customerRepository.finByEmail(loginRequest.getUsername());

        // FIXME use username or password is invalid
        String password = customer.orElseThrow(() -> new ServiceLayerException("Customer %s was not found".formatted(loginRequest.getUsername()), HttpStatus.UNPROCESSABLE_ENTITY)).getPassword();

        if (!passwordEncoder.matches(loginRequest.getPassword(), password)) {
            throw new ServiceLayerException("Password is invalid", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Session session = Session.builder()
                .customer(customer.get())
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
