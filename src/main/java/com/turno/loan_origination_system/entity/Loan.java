package com.turno.loan_origination_system.entity;

import com.turno.loan_origination_system.entity.enums.LoanStatus;
import com.turno.loan_origination_system.entity.enums.LoanType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "loans",
        indexes = {
                @Index(name="idx_loan_status", columnList = "status"),
                @Index(name="idx_loan_customer", columnList = "customer_id")
        }
)
@Getter
@Setter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "assigned_agent_id")
    private User assignedAgent;

    @Column(nullable = false)
    private Double loanAmount;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status=LoanStatus.APPLIED;
    }
}
