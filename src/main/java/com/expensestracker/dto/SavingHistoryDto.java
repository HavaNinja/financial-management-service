package com.expensestracker.dto;

import com.expensestracker.entity.Operation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SavingHistoryDto {
    private BigDecimal changeAmount;

    private LocalDateTime changeDate;

    private Operation operation;

    private BigDecimal balanceBefore;

    private BigDecimal balanceAfter;
}
