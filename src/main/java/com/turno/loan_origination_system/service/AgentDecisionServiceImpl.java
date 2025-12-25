package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.User;
import com.turno.loan_origination_system.entity.enums.AgentDecision;
import com.turno.loan_origination_system.entity.enums.AgentStatus;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.notification.NotificationService;
import com.turno.loan_origination_system.repository.LoanRepository;
import com.turno.loan_origination_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentDecisionServiceImpl implements AgentDecisionService {
    private final LoanRepository loanRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public void decide(Long agentId, Long loanId, AgentDecision decision) {
        Loan loan = loanRepository.findLoanByIdForProcessing(loanId)
                .orElseThrow(()->new IllegalStateException("loan not found"));

        if(!loan.getAssignedAgent().getId().equals(agentId)) {
            throw new IllegalStateException("Agent is not assigned to loan");
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
        User agent = loan.getAssignedAgent();
        agent.setAgentStatus(AgentStatus.AVAILABLE);

        loanRepository.save(loan);
        userRepository.save(agent);

    }
}
