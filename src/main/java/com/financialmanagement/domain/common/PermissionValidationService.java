package com.financialmanagement.domain.common;

import com.financialmanagement.domain.expense.ExpenseRepository;
import com.financialmanagement.domain.saving.SavingRepository;
import com.financialmanagement.utils.exception.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PermissionValidationService {
    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public void checkSavingBelongsToUser(String username, UUID savingId) {
        if (!savingRepository.savingBelongsToCustomer(username, savingId)) {
            throw new ServiceLayerException("Saving does not belong to customer", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void checkExpenseBelongsToUser(String username, UUID expenseId) {
        if (!expenseRepository.expenseBelongsToCustomer(username, expenseId)) {
            throw new ServiceLayerException("Expense does not belong to customer", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
