package com.turno.loan_origination_system.dto;

import com.turno.loan_origination_system.entity.enums.AgentDecision;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AgentDecisionRequest {

    @NotNull
    private AgentDecision decision;
}
