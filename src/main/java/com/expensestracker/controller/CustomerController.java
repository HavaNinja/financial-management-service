package com.expensestracker.controller;

import com.expensestracker.dto.SavingDto;
import com.expensestracker.entity.Saving;
import com.expensestracker.service.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private SavingService savingService;

    @GetMapping("/saving")
    public List<SavingDto> getAllCustomerSavings(Authentication authentication) {
        return savingService.getSavingsForCustomer(authentication.getName()).stream()
                .map(this::mapSavingToDto)
                .toList();
    }

    private SavingDto mapSavingToDto(Saving saving) {
        return SavingDto.builder()
                .amount(saving.getAmount())
                .currency(saving.getCurrency())
                .id(saving.getId())
                .name(saving.getName())
                .build();
    }

}
