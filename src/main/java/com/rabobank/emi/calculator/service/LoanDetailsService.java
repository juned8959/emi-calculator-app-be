package com.rabobank.emi.calculator.service;

import com.rabobank.emi.calculator.dataaccess.LoanDetailsManager;
import com.rabobank.emi.calculator.mapper.ToLoanDetailsMapper;
import com.rabobank.emi.calculator.model.request.LoanDetailsRequestModel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.rabobank.emi.calculator.util.EmiCalculatorUtils.computeEmi;

/**
 * The LoanDetailsService class provides services related to loan details, including EMI calculation
 */
@Service
@RequiredArgsConstructor
public class LoanDetailsService {

    private final LoanDetailsManager loanDetailsManager;

    private final ToLoanDetailsMapper toLoanDetailsMapper;

    /**
     * Computes the Equated Monthly Installment (EMI) based on the loan details.
     *
     * @param emiRequest LoanDetailsRequestModel containing loan details.
     * @return emi The computed EMI amount.
     */
    public double calculateEmi(LoanDetailsRequestModel emiRequest) {
        double emi = computeEmi(
                emiRequest.getPrincipal(),
                emiRequest.getYearlyRateOfInterest(),
                emiRequest.getTenureInYears()
        );
        loanDetailsManager.saveLoanDetails(toLoanDetailsMapper.map(emiRequest, emi));
        return emi;
    }
}