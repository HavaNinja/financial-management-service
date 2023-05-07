package com.financialmanagement.domain.expense;

import com.financialmanagement.domain.expense.dto.ExpenseCategory;
import com.financialmanagement.domain.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    @Query("SELECT e from Expense e where e.category = COALESCE(:category,e.category) AND e.date = COALESCE(:localDate,e.date) and e.customer.email=:username")
    List<Expense> finByCategory(ExpenseCategory category, LocalDate localDate, String username);
}
