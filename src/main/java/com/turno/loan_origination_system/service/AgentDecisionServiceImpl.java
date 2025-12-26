package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.entity.AgentStatusEntity;
import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.User;
import com.turno.loan_origination_system.entity.enums.AgentDecision;
import com.turno.loan_origination_system.entity.enums.AgentStatus;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.exception.LoanNotFoundException;
import com.turno.loan_origination_system.exception.UnauthorizedAgentException;
import com.turno.loan_origination_system.notification.NotificationService;
import com.turno.loan_origination_system.repository.AgentStatusRepository;
import com.turno.loan_origination_system.repository.LoanRepository;
import com.turno.loan_origination_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentDecisionServiceImpl implements AgentDecisionService {
    private final LoanRepository loanRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final AgentStatusRepository agentStatusRepository;

    @Transactional
    public void decide(Long agentId, Long loanId, AgentDecision decision) {
        Loan loan = loanRepository.findLoanByIdForProcessing(loanId)
                .orElseThrow(()->new LoanNotFoundException(loanId));

        if(loan.getAssignedAgent()==null || !loan.getAssignedAgent().getId().equals(agentId)) {
            throw new UnauthorizedAgentException(agentId, loanId);
        }

        if(loan.getStatus()!= LoanStatus.UNDER_REVIEW){
            return;
        }
        if(decision==AgentDecision.APPROVE){
            loan.setStatus(LoanStatus.APPROVED_BY_AGENT);
            notificationService.notifyCustomerApproval(loan);
        } else if(decision==AgentDecision.REJECT){
            loan.setStatus(LoanStatus.REJECTED_BY_AGENT);
        }
        AgentStatusEntity agentStatus =
                agentStatusRepository.findByAgentId(agentId)
                        .orElseThrow(() ->
                                new IllegalStateException("Agent status not found"));

        agentStatus.setStatus(AgentStatus.AVAILABLE);

        loanRepository.save(loan);
        agentStatusRepository.save(agentStatus);

    }
}
