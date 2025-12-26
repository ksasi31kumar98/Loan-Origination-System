package com.turno.loan_origination_system.repository;

import com.turno.loan_origination_system.entity.AgentStatusEntity;
import com.turno.loan_origination_system.entity.enums.AgentStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AgentStatusRepository extends JpaRepository<AgentStatusEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AgentStatusEntity a WHERE a.status = :status")
    List<AgentStatusEntity> findAvailableAgentsForUpdate(@Param("status") AgentStatus status);

    Optional<AgentStatusEntity> findByAgentId(Long agentId);
}
