package com.financialmanagement.security.jwt;

import com.financialmanagement.repository.CustomerRepository;
import com.financialmanagement.security.CustomerDetails;
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
