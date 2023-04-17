package com.financialmanagement.domain.common;

import com.financialmanagement.utils.exception.ServiceLayerException;
import com.financialmanagement.domain.saving.SavingRepository;
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
