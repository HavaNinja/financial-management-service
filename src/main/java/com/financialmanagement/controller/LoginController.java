package com.financialmanagement.controller;

import com.financialmanagement.dto.LoginRequest;
import com.financialmanagement.dto.LoginResponse;
import com.financialmanagement.service.LoginService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

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
