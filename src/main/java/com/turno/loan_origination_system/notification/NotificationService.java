package com.turno.loan_origination_system.notification;

import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.User;

public interface NotificationService {
    void notifyAgentAssignment(User agent, Loan loan);
    void notifyCustomerApproval(Loan loan);
}
