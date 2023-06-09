package com.financialmanagement.domain.expense.dto;

import com.financialmanagement.utils.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AddExpenseRequest {

    @NotNull
    private ExpenseCategory category;

    @NotNull
    private String message;

    @NotNull
    private LocalDate date;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Currency currency;
}
