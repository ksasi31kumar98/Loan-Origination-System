package com.turno.loan_origination_system.controller;

import com.turno.loan_origination_system.dto.AgentDecisionRequest;
import com.turno.loan_origination_system.service.AgentDecisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/agents")
@RequiredArgsConstructor
public class AgentController {
    private final AgentDecisionService agentDecisionService;


    @PutMapping("/{agentId}/loans/{loanId}/decision")
    public ResponseEntity<Map<String, String>> decideOnLoan(
            @PathVariable Long agentId,
            @PathVariable Long loanId,
            @Valid @RequestBody AgentDecisionRequest request
    ) {
        agentDecisionService.decide(agentId, loanId, request.getDecision());
        return ResponseEntity.ok(
                Map.of("message", "Loan decision processed successfully")
        );


    }
}
