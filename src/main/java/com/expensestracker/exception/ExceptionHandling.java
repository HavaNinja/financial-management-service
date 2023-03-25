package com.expensestracker.exception;

import com.expensestracker.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice()
public class ExceptionHandling {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalServerError(final Exception e) {
        log.error(e.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(ServiceLayerException.class)
    public ResponseEntity<ErrorResponse> internalServerError(final ServiceLayerException e) {
        log.error(e.getMessage());
        return createHttpResponse(e.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> internalServerError(final BadCredentialsException e) {
        log.error(e.getMessage());
        return createHttpResponse(UNAUTHORIZED, e.getMessage());
    }

    private ResponseEntity<ErrorResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        final ErrorResponse httpResponse = new ErrorResponse(httpStatus.value(), httpStatus, message, LocalDateTime.now());
        return new ResponseEntity<>(httpResponse, httpStatus);
    }
}
