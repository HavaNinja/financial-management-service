package com.financialmanagement.domain.saving.entity;

import com.financialmanagement.domain.common.BaseEntity;
import com.financialmanagement.utils.Operation;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "saving_id")
    private Saving saving;
}
