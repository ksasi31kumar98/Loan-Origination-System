package com.turno.loan_origination_system.controller;


import com.turno.loan_origination_system.dto.TopCustomerResponse;
import com.turno.loan_origination_system.service.CustomerQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerQueryService customerQueryService;

    @GetMapping("/top")
    public List<TopCustomerResponse> getTopCustomers() {
        return customerQueryService.getTopCustomers();
    }
}
