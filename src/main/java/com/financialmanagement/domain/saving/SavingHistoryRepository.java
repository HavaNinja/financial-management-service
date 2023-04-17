package com.financialmanagement.domain.saving;

import com.financialmanagement.domain.saving.entity.SavingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SavingHistoryRepository extends JpaRepository<SavingHistory, UUID> {

    @Query("SELECT sh from SavingHistory sh where sh.saving.id=:savingId")
    List<SavingHistory> getSavingHistory(UUID savingId);
}
