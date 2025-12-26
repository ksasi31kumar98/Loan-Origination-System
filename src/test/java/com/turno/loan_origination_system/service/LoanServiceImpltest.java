package com.turno.loan_origination_system.service;


import com.turno.loan_origination_system.dto.LoanCreateRequest;
import com.turno.loan_origination_system.dto.LoanResponse;
import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.entity.enums.LoanType;
import com.turno.loan_origination_system.repository.LoanRepository;
import com.turno.loan_origination_system.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.turno.loan_origination_system.entity.User;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanServiceImpltest {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoanServiceImpl loanService;
    @Test
    void shouldCreateLoanWithAppliedStatus() {

        // 1️⃣ Arrange - input request
        LoanCreateRequest request = new LoanCreateRequest();
        request.setCustomerName("Sasi");
        request.setCustomerPhone("9876543210");
        request.setLoanAmount(700000.0);
        request.setLoanType(LoanType.HOME);

        // 2️⃣ Fake customer (VERY IMPORTANT)
        User customer = new User();
        customer.setId(1L);
        customer.setName("Sasi");
        customer.setPhone("9876543210");

        // 3️⃣ Mock user repository behavior
        when(userRepository.findByPhone("9876543210"))
                .thenReturn(Optional.of(customer));

        // 4️⃣ Mock loan save
        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // 5️⃣ Act
        LoanResponse response = loanService.createLoan(request);

        // 6️⃣ Assert
        assertNotNull(response);
        assertEquals(LoanStatus.APPLIED, response.getStatus());
        assertEquals(700000.0, response.getLoanAmount());
        assertEquals("Sasi", response.getCustomerName());
    }


}
