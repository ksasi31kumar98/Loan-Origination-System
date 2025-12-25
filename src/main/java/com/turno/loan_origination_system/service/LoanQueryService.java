package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.dto.LoanResponse;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Map;

public interface LoanQueryService {

    Map<LoanStatus,Long> getLoanStatusCounts();
    Page<LoanResponse> getLoansByStatus(LoanStatus status, Pageable pageable);
}
