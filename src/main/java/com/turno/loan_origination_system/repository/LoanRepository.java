package com.turno.loan_origination_system.repository;

import com.turno.loan_origination_system.entity.Loan;
import com.turno.loan_origination_system.entity.enums.LoanStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Loan l WHERE l.id=:loanId")
    Optional<Loan> findLoanByIdForProcessing(@Param("loanId") Long loanId);


    @Query("SELECT l FROM Loan l WHERE l.status = :status ORDER BY l.createdAt")
    List<Loan> findLoansForProcessing(@Param("status") LoanStatus status);

    @Query("SELECT l.status,count(l) FROM Loan l GROUP BY l.status")
    List<Object[]> countLoansByStatus();

    @Query("SELECT l.customer,COUNT(l) from Loan l WHERE l.status IN (LoanStatus.APPROVED_BY_SYSTEM,LoanStatus.APPROVED_BY_AGENT) GROUP BY l.customer ORDER BY COUNT(l) DESC")
    List<Object[]> findTopCustomers(Pageable pageable);

    Page<Loan> findByStatus(LoanStatus status, Pageable pageable);
}
