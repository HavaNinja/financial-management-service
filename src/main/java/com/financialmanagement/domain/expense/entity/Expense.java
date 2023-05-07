package com.financialmanagement.domain.expense.entity;

import com.financialmanagement.domain.common.BaseEntity;
import com.financialmanagement.domain.customer.entity.Customer;
import com.financialmanagement.domain.expense.dto.ExpenseCategory;
import com.financialmanagement.utils.Currency;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends BaseEntity {

    private ExpenseCategory category;

    private String message;

    private LocalDate date;

    private BigDecimal amount;

    private Currency currency;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;
}
