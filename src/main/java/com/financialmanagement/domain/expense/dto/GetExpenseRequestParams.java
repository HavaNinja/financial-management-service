package com.financialmanagement.domain.expense.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetExpenseRequestParams {

    private ExpenseCategory category;
    private LocalDate date;
}
