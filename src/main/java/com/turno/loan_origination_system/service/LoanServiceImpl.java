package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.dto.LoanCreateRequest;
import com.turno.loan_origination_system.dto.LoanResponse;
import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.User;
import com.turno.loan_origination_system.entity.enums.UserRole;
import com.turno.loan_origination_system.repository.LoanRepository;
import com.turno.loan_origination_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    @Override
    public LoanResponse createLoan(LoanCreateRequest loanCreateRequest) {
        User customer=userRepository.findByPhone(loanCreateRequest.getCustomerPhone()).orElse(null);
        if(customer == null){
            customer = new User();
            customer.setName(loanCreateRequest.getCustomerName());
            customer.setPhone(loanCreateRequest.getCustomerPhone());
            customer.setRole(UserRole.CUSTOMER);
            customer=userRepository.save(customer);
        }
        Loan loan=new Loan();
        loan.setCustomer(customer);
        loan.setLoanAmount(loanCreateRequest.getLoanAmount());
        loan.setLoanType(loanCreateRequest.getLoanType());
        Loan savedLoan=loanRepository.save(loan);

        return mapToResponse(savedLoan);

    }
    private LoanResponse mapToResponse(Loan loan) {
        LoanResponse response = new LoanResponse();
        response.setLoanId(loan.getId());
        response.setCustomerName(loan.getCustomer().getName());
        response.setCustomerPhone(loan.getCustomer().getPhone());
        response.setLoanAmount(loan.getLoanAmount());
        response.setLoanType(loan.getLoanType());
        response.setStatus(loan.getStatus());
        return response;
    }

}
