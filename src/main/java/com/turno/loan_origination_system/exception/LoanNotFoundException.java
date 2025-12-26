package com.turno.loan_origination_system.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Long loanId) {
        super("Loan with id " + loanId + " not found");
    }
}
