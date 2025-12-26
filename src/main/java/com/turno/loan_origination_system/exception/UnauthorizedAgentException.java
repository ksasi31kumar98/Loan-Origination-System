package com.turno.loan_origination_system.exception;

public class UnauthorizedAgentException extends RuntimeException {
    public UnauthorizedAgentException(Long agentId, Long loanId) {
        super("Agent " + agentId + " is not assigned to loan " + loanId);
    }
}
