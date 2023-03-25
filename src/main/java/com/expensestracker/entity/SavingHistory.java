package com.expensestracker.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class SavingHistory extends BaseEntity {

    private BigDecimal changeAmount;

    private LocalDateTime changeDate;

    private Operation operation;

    private BigDecimal balanceBefore;

    private BigDecimal balanceAfter;
}
