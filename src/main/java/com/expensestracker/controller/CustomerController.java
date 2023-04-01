package com.expensestracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {


    @GetMapping
    public String test(Authentication authentication) {

        return "Hi %s".formatted(authentication.getName());

    }

}
