package com.financialmanagement.domain.customer.entity;

import com.financialmanagement.domain.common.BaseEntity;
import com.financialmanagement.domain.expense.entity.Expense;
import com.financialmanagement.domain.saving.entity.Saving;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "customer_id")
    private List<Saving> savings;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customer_id")
    private List<Expense> expenses;

    private String password;

}
