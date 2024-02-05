package com.rabobank.emi.calculator.mapper;

import com.rabobank.emi.calculator.entity.LoanDetails;
import com.rabobank.emi.calculator.model.request.LoanDetailsRequestModel;
import org.springframework.stereotype.Component;

@Component
public class ToLoanDetailsMapper {

    public LoanDetails map(final LoanDetailsRequestModel loanDetailsRequestModel, final double emi) {
        return LoanDetails.builder()
                .principal(loanDetailsRequestModel.getPrincipal())
                .yearlyRateOfInterest(loanDetailsRequestModel.getYearlyRateOfInterest())
                .tenureInYears(loanDetailsRequestModel.getTenureInYears())
                .emailId(loanDetailsRequestModel.getEmail())
                .monthlyInstallment(emi)
                .build();
    }
}
