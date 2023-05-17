package com.financialmanagement.controller;

import com.financialmanagement.domain.expense.ExpenseService;
import com.financialmanagement.domain.expense.dto.AddExpenseRequest;
import com.financialmanagement.domain.saving.SavingService;
import com.financialmanagement.domain.saving.dto.SavingDto;
import com.financialmanagement.domain.saving.entity.Saving;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private SavingService savingService;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/saving")
    public List<SavingDto> getAllCustomerSavings(Authentication authentication) {
        return savingService.getSavingsForCustomer(authentication.getName()).stream()
                .map(this::mapSavingToDto)
                .toList();
    }

    @PostMapping("/expense")
    @ResponseStatus(HttpStatus.CREATED)
    public void addExpense(@RequestBody @Valid AddExpenseRequest addExpenseRequest) {
        expenseService.addExpense(addExpenseRequest);
    }

    private SavingDto mapSavingToDto(Saving saving) {
        return SavingDto.builder()
                .amount(saving.getAmount())
                .currency(saving.getCurrency())
                .id(saving.getId())
                .name(saving.getName())
                .build();
    }

}
