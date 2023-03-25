package com.expensestracker.controller;

import com.expensestracker.dto.LoginRequest;
import com.expensestracker.dto.LoginResponse;
import com.expensestracker.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }

    @GetMapping("/logout")
    public void logout(Authentication authentication) {
        String principal = (String) authentication.getPrincipal();
        loginService.logOut(UUID.fromString(principal));
    }
}
