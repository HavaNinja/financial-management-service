package com.expensestracker.repository;

import com.expensestracker.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SavingRepository extends JpaRepository<Saving, UUID> {

    @Query("SELECT s FROM Saving s WHERE s.customer.email=:username")
    List<Saving> getSavingsForCustomer(String username);

    @Query("SELECT count (s) > 0 FROM Saving s where s.customer.email=:username AND s.id=:savingId")
    boolean savingBelongsToCustomer(String username, UUID savingId);
}
