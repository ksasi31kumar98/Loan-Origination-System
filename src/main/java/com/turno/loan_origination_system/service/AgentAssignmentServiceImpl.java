package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.User;
import com.turno.loan_origination_system.entity.enums.AgentStatus;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.entity.enums.UserRole;
import com.turno.loan_origination_system.notification.NotificationService;
import com.turno.loan_origination_system.repository.LoanRepository;
import com.turno.loan_origination_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AgentAssignmentServiceImpl implements AgentAssignmentService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    @Override
    public void assignAgentToLoan(Long loanId) {
        Loan loan = loanRepository.findLoanByIdForProcessing(loanId).orElseThrow(()->new IllegalStateException("Loan with id " + loanId + " not found"));
        if(loan.getStatus()!= LoanStatus.UNDER_REVIEW){
            return;
        }
        User agent = userRepository.findAvailableAgentsForUpdate().stream().findFirst()
                .orElseThrow(()->new IllegalStateException("No available agents found"));
        loan.setAssignedAgent(agent);
        agent.setAgentStatus(AgentStatus.BUSY);

        loanRepository.save(loan);
        userRepository.save(agent);

        notificationService.notifyAgentAssignment(agent, loan);

    }
}
