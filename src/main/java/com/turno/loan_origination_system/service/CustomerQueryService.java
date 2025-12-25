package com.turno.loan_origination_system.service;

import com.turno.loan_origination_system.dto.TopCustomerResponse;

import java.util.List;

public interface CustomerQueryService {
    List<TopCustomerResponse> getTopCustomers();
}
