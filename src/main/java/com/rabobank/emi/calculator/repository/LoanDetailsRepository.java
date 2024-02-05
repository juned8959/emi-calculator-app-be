package com.rabobank.emi.calculator.repository;

import com.rabobank.emi.calculator.entity.LoanDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanDetailsRepository extends JpaRepository<LoanDetails, Long> {
}