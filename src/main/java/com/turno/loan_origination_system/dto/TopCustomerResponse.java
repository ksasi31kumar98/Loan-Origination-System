package com.turno.loan_origination_system.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopCustomerResponse {
    private String customerName;
    private String customerPhone;
    private Long approvedLoanCount;
}
