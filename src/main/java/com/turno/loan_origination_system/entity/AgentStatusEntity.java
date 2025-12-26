package com.turno.loan_origination_system.entity;

import com.turno.loan_origination_system.entity.enums.AgentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "agent_status")
@Getter
@Setter
public class AgentStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User agent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgentStatus status;
}

