package com.expensestracker.service;

import com.expensestracker.dto.ModifySavingDto;
import com.expensestracker.dto.OpenSavingRequest;
import com.expensestracker.entity.Customer;
import com.expensestracker.entity.Operation;
import com.expensestracker.entity.Saving;
import com.expensestracker.entity.SavingHistory;
import com.expensestracker.exception.ServiceLayerException;
import com.expensestracker.repository.CustomerRepository;
import com.expensestracker.repository.SavingHistoryRepository;
import com.expensestracker.repository.SavingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SavingService {

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SavingHistoryRepository savingHistoryRepository;

    @Autowired
    private PermissionValidationService permissionValidationService;

    public void openSaving(OpenSavingRequest openSavingRequest) {

        Saving newSaving = new Saving();
        newSaving.setAmount(openSavingRequest.getAmount());
        newSaving.setCurrency(openSavingRequest.getCurrency());
        newSaving.setName(openSavingRequest.getName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.finByEmail(authentication.getName()).get();
        newSaving.setCustomer(customer);

        savingRepository.save(newSaving);
    }

    public List<Saving> getSavingsForCustomer(String username) {
        return savingRepository.getSavingsForCustomer(username);
    }

    public List<SavingHistory> getSavingHistory(UUID savingId) {
        return savingHistoryRepository.getSavingHistory(savingId);
    }

    @Transactional
    public void modifySaving(ModifySavingDto modifySavingDto) {
        Saving savingToModify = savingRepository.findById(modifySavingDto.getSavingId()).orElseThrow(() -> new ServiceLayerException("Saving was not found", HttpStatus.NOT_FOUND));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        permissionValidationService.checkSavingBelongsToUser(username, modifySavingDto.getSavingId());

        BigDecimal balanceBeforeOperation = savingToModify.getAmount();
        if (modifySavingDto.getOperation().equals(Operation.DEPOSIT)) {
            savingToModify.setAmount(balanceBeforeOperation.add(modifySavingDto.getAmount()));
        } else {
            savingToModify.setAmount(balanceBeforeOperation.subtract(modifySavingDto.getAmount()));
        }

        SavingHistory savingHistory = new SavingHistory();
        savingHistory.setChangeAmount(modifySavingDto.getAmount());
        savingHistory.setBalanceBefore(balanceBeforeOperation);
        savingHistory.setBalanceAfter(savingToModify.getAmount());
        savingHistory.setOperation(modifySavingDto.getOperation());
        savingHistory.setChangeDate(LocalDateTime.now());
        savingHistory.setSaving(savingToModify);

        savingToModify.getHistory().add(savingHistory);

        savingRepository.save(savingToModify);

    }

    public void remove(UUID savingId) {
        Optional<Saving> savingToRemove = savingRepository.findById(savingId);
        savingToRemove.ifPresent(saving -> savingRepository.delete(saving));
    }
}
