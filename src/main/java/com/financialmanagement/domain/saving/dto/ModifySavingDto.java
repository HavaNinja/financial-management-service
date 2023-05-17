package com.financialmanagement.domain.saving.dto;

import com.financialmanagement.utils.Operation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ModifySavingDto {
    @NotNull
    @NotEmpty
    private UUID savingId;

    @NotNull
    @NotEmpty
    private Operation operation;

    @NotNull
    @NotEmpty
    private BigDecimal amount;
}
