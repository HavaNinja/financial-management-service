package com.expensestracker.dto;

import com.expensestracker.entity.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OpenSavingRequest {

    private BigDecimal amount;

    private Currency currency;
}
