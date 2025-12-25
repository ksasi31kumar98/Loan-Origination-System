package com.turno.loan_origination_system.entity;


import com.turno.loan_origination_system.entity.enums.AgentStatus;
import com.turno.loan_origination_system.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private AgentStatus agentStatus;

    @ManyToOne
    @JoinColumn(name="manager_id")
    private User manager;
}
