package com.turno.loan_origination_system.dto;


import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.entity.enums.LoanType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoanResponse {

    private Long loanId;
    private String customerName;
    private String customerPhone;
    private Double loanAmount;
    private LoanType loanType;
    private LoanStatus status;
    private LocalDateTime createdAt;
}
