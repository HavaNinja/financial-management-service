package com.financialmanagement.dto;

import com.financialmanagement.entity.Operation;
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
