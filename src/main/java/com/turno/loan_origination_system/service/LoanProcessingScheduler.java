package com.turno.loan_origination_system.service;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanProcessingScheduler {

    private final LoanProcessingService loanProcessingService;

    @Scheduled(fixedDelay = 10000, initialDelay = 15000)
    public void processPendingLoans() {
        loanProcessingService.processPendingLoans();
    }
}
