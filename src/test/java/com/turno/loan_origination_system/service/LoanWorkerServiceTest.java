package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class LoanWorkerServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private AgentAssignmentService agentAssignmentService;

    @InjectMocks
    private LoanWorkerServiceImpl loanWorkerService;

    private Loan createLoan(Long id, double amount) {
        Loan loan = new Loan();
        loan.setId(id);
        loan.setLoanAmount(amount);
        loan.setStatus(LoanStatus.APPLIED);
        loan.setAssignedAgent(null);
        return loan;
    }

    @Test
    void shouldApproveLoan_WhenAmountIsLessThan500k() {
        Loan loan = createLoan(1L, 400_000);

        when(loanRepository.findLoanByIdForProcessing(1L))
                .thenReturn(Optional.of(loan));

        loanWorkerService.processSingleLoan(1L);

        assertEquals(LoanStatus.APPROVED_BY_SYSTEM, loan.getStatus());
        verify(loanRepository).save(loan);
        verify(agentAssignmentService, never()).assignAgentToLoan(any());
    }

    @Test
    void shouldRejectLoan_WhenAmountIsGreaterThanOrEqualTo1Million() {
        Loan loan = createLoan(2L, 1_500_000);

        when(loanRepository.findLoanByIdForProcessing(2L))
                .thenReturn(Optional.of(loan));

        loanWorkerService.processSingleLoan(2L);

        assertEquals(LoanStatus.REJECTED_BY_SYSTEM, loan.getStatus());
        verify(loanRepository).save(loan);
        verify(agentAssignmentService, never()).assignAgentToLoan(any());
    }

    @Test
    void shouldAssignAgent_WhenLoanNeedsManualReview() {
        Loan loan = createLoan(3L, 700_000);

        when(loanRepository.findLoanByIdForProcessing(3L))
                .thenReturn(Optional.of(loan));

        loanWorkerService.processSingleLoan(3L);

        assertEquals(LoanStatus.UNDER_REVIEW, loan.getStatus());
        verify(loanRepository).save(loan);
        verify(agentAssignmentService).assignAgentToLoan(3L);
    }
    @Test
    void shouldDoNothing_WhenLoanIsAlreadyProcessed() {
        
        Loan loan = new Loan();
        loan.setId(4L);
        loan.setLoanAmount(300000.00);
        loan.setStatus(LoanStatus.APPROVED_BY_SYSTEM); // already processed

        when(loanRepository.findLoanByIdForProcessing(4L))
                .thenReturn(Optional.of(loan));


        loanWorkerService.processSingleLoan(4L);


        assertEquals(LoanStatus.APPROVED_BY_SYSTEM, loan.getStatus());


        verify(loanRepository, never()).save(any());
        verify(agentAssignmentService, never()).assignAgentToLoan(any());
    }

}







