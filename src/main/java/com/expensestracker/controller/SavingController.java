package com.expensestracker.controller;

import com.expensestracker.dto.ModifySavingDto;
import com.expensestracker.dto.OpenSavingRequest;
import com.expensestracker.dto.SavingHistoryDto;
import com.expensestracker.entity.SavingHistory;
import com.expensestracker.service.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/saving")
public class SavingController {

    @Autowired
    private SavingService savingService;

    @PostMapping
    public void openSaving(Authentication authentication, @RequestBody OpenSavingRequest openSavingRequest) {
        savingService.openSaving(openSavingRequest);
    }

    @GetMapping("/history/{savingId}")
    public List<SavingHistoryDto> getSavingHistory(@PathVariable UUID savingId) {
        return savingService.getSavingHistory(savingId).stream().map(this::mapToSavingHistoryDto).toList();
    }

    @PostMapping("/modify")
    public void modifySaving(@RequestBody @Valid ModifySavingDto modifySavingDto) {
        savingService.modifySaving(modifySavingDto);
    }

    @DeleteMapping("/{savingId}")
    public void remove(@PathVariable UUID savingId) {
        savingService.remove(savingId);
    }

    private SavingHistoryDto mapToSavingHistoryDto(SavingHistory savingHistory) {
        return SavingHistoryDto.builder()
                .changeAmount(savingHistory.getChangeAmount())
                .changeDate(savingHistory.getChangeDate())
                .operation(savingHistory.getOperation())
                .balanceBefore(savingHistory.getBalanceBefore())
                .balanceAfter(savingHistory.getBalanceAfter())
                .build();
    }

}
