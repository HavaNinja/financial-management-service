package com.financialmanagement.domain.customer;

import com.financialmanagement.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    boolean existsByEmail(String email);

    @Query(value = "select * from Customer c where c.email=?", nativeQuery = true)
    Optional<Customer> finByEmail(String email);


    @Query(value = " SELECT count (s.customer)=1 FROM Session s WHERE s.id = ?1  AND s.active = true ")
    boolean existsBySessionId(UUID sessionId);

    @Query(value = " SELECT s.customer FROM Session s  WHERE s.id = ?1 AND s.active = true")
    Optional<Customer> findCustomerBySession(UUID sessionId);
}
