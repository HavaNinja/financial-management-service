package com.financialmanagement.dto;

import com.financialmanagement.entity.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OpenSavingRequest {

    private BigDecimal amount;

    private Currency currency;

    private String name;
}
