package com.financialmanagement.domain.saving.dto;

import com.financialmanagement.utils.Operation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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