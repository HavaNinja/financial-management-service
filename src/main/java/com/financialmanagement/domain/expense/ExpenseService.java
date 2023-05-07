package com.financialmanagement.domain.expense;

import com.financialmanagement.domain.customer.CustomerRepository;
import com.financialmanagement.domain.customer.entity.Customer;
import com.financialmanagement.domain.expense.dto.AddExpenseRequest;
import com.financialmanagement.domain.expense.dto.GetExpenseRequestParams;
import com.financialmanagement.domain.expense.entity.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CustomerRepository customerRepository;

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
}
