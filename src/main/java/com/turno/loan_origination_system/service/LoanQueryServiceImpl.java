package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.dto.LoanResponse;
import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
@RequiredArgsConstructor
public class LoanQueryServiceImpl implements LoanQueryService {
    private final LoanRepository loanRepository;
    @Override
    public Map<LoanStatus, Long> getLoanStatusCounts() {
        Map<LoanStatus, Long> result=new EnumMap<>(LoanStatus.class);
        List<Object[]> rows=loanRepository.countLoansByStatus();
        for(Object[] row:rows){
            LoanStatus status=(LoanStatus) row[0];
            Long count=(Long) row[1];
            result.put(status,count);
        }
        return result;
    }



    @Override
    public Page<LoanResponse> getLoansByStatus(LoanStatus status, Pageable pageable) {
        Page<Loan> page=loanRepository.findByStatus(status,pageable);
        List<LoanResponse> responses=new ArrayList<>();
        for (Loan loan : page.getContent()) {
            LoanResponse r = new LoanResponse();
            r.setLoanId(loan.getId());
            r.setCustomerName(loan.getCustomer().getName());
            r.setCustomerPhone(loan.getCustomer().getPhone());
            r.setLoanAmount(loan.getLoanAmount());
            r.setLoanType(loan.getLoanType());
            r.setStatus(loan.getStatus());
            r.setCreatedAt(loan.getCreatedAt());
            responses.add(r);
        }

        return new PageImpl<LoanResponse>(responses, pageable, page.getTotalElements());

    }
}
