package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.entity.enums.AgentDecision;

public interface AgentDecisionService {
    void decide(Long agentId, Long loanId, AgentDecision decision);
}
