package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.dto.LoanCreateRequest;
import com.turno.loan_origination_system.dto.LoanResponse;
import com.turno.loan_origination_system.entity.Loan;

public interface LoanService {
    LoanResponse createLoan(LoanCreateRequest loanCreateRequest);
}
