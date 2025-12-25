package com.turno.loan_origination_system.controller;

import com.turno.loan_origination_system.dto.LoanCreateRequest;
import com.turno.loan_origination_system.dto.LoanResponse;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.service.LoanQueryService;
import com.turno.loan_origination_system.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private final LoanQueryService loanQueryService;
    @PostMapping
    public ResponseEntity<LoanResponse> submitLoan(
            @Valid @RequestBody LoanCreateRequest request
    ) {
        LoanResponse response = loanService.createLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/status-count")
    public Map<LoanStatus,Long> getStatusCount(){
        return loanQueryService.getLoanStatusCounts();
    }

    @GetMapping
    public Page<LoanResponse> getLoanByStatus(
            @RequestParam LoanStatus status,
            Pageable pageable
    ){
        return loanQueryService.getLoansByStatus(status,pageable);
    }

}
