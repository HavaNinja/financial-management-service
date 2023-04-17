package com.financialmanagement.auth.jwt;

import com.financialmanagement.domain.customer.CustomerRepository;
import com.financialmanagement.auth.CustomerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var customer = customerRepository.finByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Ser with username %s does not exist".formatted(username)));
        return new CustomerDetails(customer);

    }
}
