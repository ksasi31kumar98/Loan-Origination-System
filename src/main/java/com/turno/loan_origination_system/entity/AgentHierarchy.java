package com.turno.loan_origination_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "agent_hierarchy")
@Getter
@Setter
public class AgentHierarchy {

    @Id
    @Column(name = "agent_id")
    private Long agentId;

    @Column(name = "manager_id")
    private Long managerId;
}
