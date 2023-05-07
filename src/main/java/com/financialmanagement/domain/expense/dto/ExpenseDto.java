package com.financialmanagement.domain.expense.dto;

import com.financialmanagement.utils.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ExpenseDto {

    private ExpenseCategory category;

    private String message;

    private LocalDate date;

    private BigDecimal amount;

    private Currency currency;
}
