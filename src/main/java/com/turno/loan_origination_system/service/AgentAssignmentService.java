package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.entity.Loan;

public interface AgentAssignmentService {
    void assignAgentToLoan(Long loanId);
}
