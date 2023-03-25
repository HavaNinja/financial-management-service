package com.expensestracker.repository;

import com.expensestracker.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    @Modifying
    @Query("update Session  set active=false where id=?1")
    void invalidateSession(UUID sessionId);
}
