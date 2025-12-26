package com.turno.loan_origination_system.repository;

import com.turno.loan_origination_system.entity.User;
import com.turno.loan_origination_system.entity.enums.AgentStatus;
import com.turno.loan_origination_system.entity.enums.UserRole;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhone(String phone);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("SELECT u FROM User u WHERE u.role='AGENT' AND u.agentStatus='AVAILABLE'")
//    List<User> findAvailableAgentsForUpdate();
@Query("SELECT u FROM User u WHERE u.id = (SELECT ah.managerId FROM AgentHierarchy ah WHERE ah.agentId = :agentId)")
Optional<User> findManagerForAgent(@Param("agentId") Long agentId);

}
