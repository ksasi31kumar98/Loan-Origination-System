package com.turno.loan_origination_system.notification;

import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class MockNotificationService implements NotificationService {

    private static final Logger log =
            LoggerFactory.getLogger(MockNotificationService.class);

    @Override
    public void notifyAgentAssignment(User agent, Loan loan) {
        log.info(
                "Notification sent to agent {} for loan {}",
                agent.getName(),
                loan.getId()
        );
    }

    @Override
    public void notifyManagerAssignment(User manager, Loan loan) {
        log.info(
                "Notification sent to manager {} for loan {}",
                manager.getName(),
                loan.getId()
        );
    }

    @Override
    public void notifyCustomerApproval(Loan loan) {
        log.info(
                "SMS -> Loan {} approved. Customer phone={}",
                loan.getId(),
                loan.getCustomer().getPhone()
        );
    }
}
