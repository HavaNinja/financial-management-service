package com.financialmanagement.controller;

import com.financialmanagement.domain.expense.ExpenseService;
import com.financialmanagement.domain.expense.dto.ExpenseDto;
import com.financialmanagement.domain.expense.dto.GetExpenseRequestParams;
import com.financialmanagement.domain.expense.entity.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    public ExpenseService expenseService;

    @GetMapping
    public List<ExpenseDto> getExpenses(@ModelAttribute GetExpenseRequestParams getExpenseRequestParams) {

        return expenseService.findByCategory(getExpenseRequestParams).stream()
                .map(this::mapToDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable UUID id) {
        expenseService.deleteExpense(id);
    }

    private ExpenseDto mapToDto(Expense expense) {
        return ExpenseDto.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .currency(expense.getCurrency())
                .date(expense.getDate())
                .category(expense.getCategory())
                .message(expense.getMessage())
                .build();
    }

}
