package com.expensestracker.controller;

import com.expensestracker.dto.OpenSavingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/saving")
public class SavingController {

    @PostMapping
    public void openSaving(OpenSavingRequest openSavingRequest) {

    }
}
