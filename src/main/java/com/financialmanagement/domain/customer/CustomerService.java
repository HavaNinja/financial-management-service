package com.financialmanagement.domain.customer;

import com.financialmanagement.domain.customer.dto.CreateCustomerRequest;
import com.financialmanagement.domain.customer.entity.Customer;
import com.financialmanagement.utils.exception.ServiceLayerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public UUID createCustomer(CreateCustomerRequest customerDto) {
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new ServiceLayerException("Customer with email [%s] already exists".formatted(customerDto.getEmail()), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var customer = Customer.builder()
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .email(customerDto.getEmail())
                .password(passwordEncoder.encode(customerDto.getPassword()))
                .build();

        return customerRepository.save(customer).getId();
    }

}
