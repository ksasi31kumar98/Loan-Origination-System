package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.dto.TopCustomerResponse;
import com.turno.loan_origination_system.entity.User;
import com.turno.loan_origination_system.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements CustomerQueryService {
    private final LoanRepository loanRepository;
    @Override
    public List<TopCustomerResponse> getTopCustomers() {
        List<Object[]> rows=loanRepository.findTopCustomers(PageRequest.of(0,3));
        List<TopCustomerResponse> result=new ArrayList<>();
        for (Object[] row : rows) {
            User customer = (User) row[0];
            Long count = (Long) row[1];

          TopCustomerResponse response = new TopCustomerResponse();
          response.setCustomerName(customer.getName());
          response.setCustomerPhone(customer.getPhone());
          response.setApprovedLoanCount(count);

            result.add(response);
        }

        return result;

    }
}
