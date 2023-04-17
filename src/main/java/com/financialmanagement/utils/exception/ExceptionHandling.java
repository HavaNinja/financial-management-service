package com.financialmanagement.utils.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice()
public class ExceptionHandling {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalServerError(final Exception e) {
        log.error(e.getMessage(), e);
        return createHttpResponse(INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(ServiceLayerException.class)
    public ResponseEntity<ErrorResponse> internalServerError(final ServiceLayerException e) {
        log.error(e.getMessage(), e);
        return createHttpResponse(e.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> internalServerError(final BadCredentialsException e) {
        log.error(e.getMessage(), e);
        return createHttpResponse(UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<ErrorResponse> internalServerError(final JWTDecodeException e) {
        log.error(e.getMessage(), e);
        return createHttpResponse(FORBIDDEN, e.getMessage());
    }

    private ResponseEntity<ErrorResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        final ErrorResponse httpResponse = new ErrorResponse(httpStatus.value(), httpStatus, message, new Date());
        return new ResponseEntity<>(httpResponse, httpStatus);
    }
}
