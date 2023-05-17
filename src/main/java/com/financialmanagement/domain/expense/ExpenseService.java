package com.financialmanagement.domain.expense;

import com.financialmanagement.domain.common.PermissionValidationService;
import com.financialmanagement.domain.customer.CustomerRepository;
import com.financialmanagement.domain.customer.entity.Customer;
import com.financialmanagement.domain.expense.dto.AddExpenseRequest;
import com.financialmanagement.domain.expense.dto.GetExpenseRequestParams;
import com.financialmanagement.domain.expense.entity.Expense;
import com.financialmanagement.utils.exception.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PermissionValidationService permissionValidationService;

    public void addExpense(AddExpenseRequest addExpenseRequest) {
        Expense expense = new Expense();
        expense.setCategory(addExpenseRequest.getCategory());
        expense.setCurrency(addExpenseRequest.getCurrency());
        expense.setMessage(addExpenseRequest.getMessage());
        expense.setDate(addExpenseRequest.getDate());
        expense.setAmount(addExpenseRequest.getAmount());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.finByEmail(username).get();

        expense.setCustomer(customer);

        expenseRepository.save(expense);
    }

    public List<Expense> findByCategory(GetExpenseRequestParams params) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return expenseRepository.finByCategory(params.getCategory(), params.getDate(), username);
    }

    public void deleteExpense(UUID id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ServiceLayerException("Expense was not found", HttpStatus.NOT_FOUND));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        permissionValidationService.checkExpenseBelongsToUser(username, expense.getId());

        expenseRepository.deleteById(expense.getId());
    }
}
