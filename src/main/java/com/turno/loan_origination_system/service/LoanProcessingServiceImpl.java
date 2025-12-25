package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LoanProcessingServiceImpl implements LoanProcessingService {
    private final LoanRepository loanRepository;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    //private final AgentAssignmentService agentAssignmentService;
    private final LoanWorkerService loanWorkerService;

    @Override

    public void processPendingLoans() {
        List<Loan> loans=loanRepository.findLoansForProcessing(LoanStatus.APPLIED);
        for(Loan loan:loans){
            threadPoolTaskExecutor.execute(()->loanWorkerService.processSingleLoan(loan.getId()));
        }
    }



}
