package com.expensestracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@Entity
public class Saving extends BaseEntity {

    private BigDecimal amount;

    private Currency currency;

    @OneToMany
    @JoinColumn(name = "saving_id")
    private List<SavingHistory> history;
}
