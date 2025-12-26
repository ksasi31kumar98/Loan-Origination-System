package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.entity.AgentStatusEntity;
import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.User;
import com.turno.loan_origination_system.entity.enums.AgentStatus;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.notification.NotificationService;
import com.turno.loan_origination_system.repository.AgentStatusRepository;
import com.turno.loan_origination_system.repository.LoanRepository;
import com.turno.loan_origination_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentAssignmentServiceImpl implements AgentAssignmentService {

    private final LoanRepository loanRepository;
    private final AgentStatusRepository agentStatusRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void assignAgentToLoan(Long loanId) {

        Loan loan = loanRepository
                .findLoanByIdForProcessing(loanId)
                .orElseThrow(() ->
                        new IllegalStateException("Loan with id " + loanId + " not found"));

        if (loan.getStatus() != LoanStatus.UNDER_REVIEW) {
            return;
        }

        AgentStatusEntity agentStatusEntity =
                agentStatusRepository
                        .findAvailableAgentsForUpdate(AgentStatus.AVAILABLE)
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalStateException("No available agents found"));


        loan.setAssignedAgent(agentStatusEntity.getAgent());

        agentStatusEntity.setStatus(AgentStatus.BUSY);

        loanRepository.save(loan);
        agentStatusRepository.save(agentStatusEntity);

        notificationService.notifyAgentAssignment(
                agentStatusEntity.getAgent(), loan
        );
        User manager = userRepository.findManagerForAgent(agentStatusEntity.getAgent().getId())
                .orElse(null);

        if (manager != null) {
            notificationService.notifyManagerAssignment(manager, loan);
        }
    }
}
