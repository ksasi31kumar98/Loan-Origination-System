package com.turno.loan_origination_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoanOriginationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanOriginationSystemApplication.class, args);
	}

}
