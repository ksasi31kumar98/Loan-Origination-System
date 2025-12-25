package com.turno.loan_origination_system.dto;


import com.turno.loan_origination_system.entity.enums.LoanType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanCreateRequest {
    @NotBlank
    private String customerName;

    @NotBlank

    private String customerPhone;

    @NotNull
    @Positive
    private Double loanAmount;

    @NotNull
    private LoanType loanType;
}
