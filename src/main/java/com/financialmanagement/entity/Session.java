package com.financialmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    @Cascade(CascadeType.REMOVE)
    private Customer customer;

    private Boolean active;
}
