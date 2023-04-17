package com.expensestracker.service;

import com.expensestracker.exception.ServiceLayerException;
import com.expensestracker.repository.SavingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PermissionValidationService {
    @Autowired
    private SavingRepository savingRepository;

    public void checkSavingBelongsToUser(String username, UUID savingId) {
        if (!savingRepository.savingBelongsToCustomer(username, savingId)) {
            throw new ServiceLayerException("Saving does not belong to customer");
        }
    }
}
