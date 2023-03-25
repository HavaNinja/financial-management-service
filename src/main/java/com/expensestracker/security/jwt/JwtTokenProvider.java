package com.expensestracker.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.expensestracker.entity.Session;
import com.expensestracker.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret:jwt-top-secret}")
    private String secret;

    private final SessionRepository sessionRepository;

    public String generateJwtToken(final String session) {

        return JWT.create()
                .withSubject(session)
                .sign(HMAC512(secret.getBytes()));
    }

    public boolean isTokenValid(String token) {
        var sessionId = getSubject(token);
        Optional<Session> session = sessionRepository.findById(UUID.fromString(sessionId));

        return session.isPresent() ? session.get().getActive() : false;
    }

    public String getSubject(String token) {
        final JWTVerifier jwtVerifier = getJwtVerifier();
        return jwtVerifier.verify(token).getSubject();
    }

    private JWTVerifier getJwtVerifier() {
        com.auth0.jwt.interfaces.JWTVerifier verifier;
        try {
            Algorithm algorithm = HMAC512(secret);
            verifier = JWT.require(algorithm).build();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("TOKEN CAN NOT BE VERIFYED");
        }
        return verifier;
    }

}
