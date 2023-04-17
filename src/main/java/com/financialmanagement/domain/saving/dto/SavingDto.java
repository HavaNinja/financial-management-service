package com.financialmanagement.domain.saving.dto;

import com.financialmanagement.utils.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SavingDto {

    private UUID id;

    private BigDecimal amount;

    private Currency currency;

    private String name;
}
