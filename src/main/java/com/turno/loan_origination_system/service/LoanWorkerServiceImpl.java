package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanWorkerServiceImpl implements LoanWorkerService {

    private final LoanRepository loanRepository;
    private final AgentAssignmentService agentAssignmentService;

    @Transactional
    @Override
    public void processSingleLoan(Long loanId) {

        Loan loan =
            loanRepository.findLoanByIdForProcessing(loanId).orElse(null);

        if (loan == null) return;
        if (loan.getStatus() != LoanStatus.APPLIED) return;

        simulateDelay();

        if (loan.getLoanAmount() < 500000) {
            loan.setStatus(LoanStatus.APPROVED_BY_SYSTEM);

        } else if (loan.getLoanAmount() > 1000000) {
            loan.setStatus(LoanStatus.REJECTED_BY_SYSTEM);

        } else {
            loan.setStatus(LoanStatus.UNDER_REVIEW);
            loanRepository.save(loan);
            agentAssignmentService.assignAgentToLoan(loan.getId());
            return;
        }

        loanRepository.save(loan);
    }

    private void simulateDelay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
