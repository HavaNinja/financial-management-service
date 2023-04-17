package com.financialmanagement.utils.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ServiceLayerException extends RuntimeException {

    private HttpStatus httpStatus;

    public ServiceLayerException(String msg) {
        super(msg);
    }

    public ServiceLayerException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

}
