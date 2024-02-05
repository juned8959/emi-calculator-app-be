package com.rabobank.emi.calculator.web;

import com.rabobank.emi.calculator.model.request.LoanDetailsRequestModel;
import com.rabobank.emi.calculator.model.response.LoanDetailsResponseModel;
import com.rabobank.emi.calculator.service.LoanDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This class defines a REST controller for handling loan calculation requests
 */
@RestController
@RequestMapping("/api/calculate")
@RequiredArgsConstructor
public class LoanDetailsRestController {

    private final LoanDetailsService loanDetailsService;

    /**
     * Calculates the monthly installment (EMI) for the given loan details.
     *
     * @param loanDetailsRequestModel The loan details request model containing parameters for EMI calculation.
     * @return LoanDetailsResponseModel containing the calculated monthly installment.
     */
    @PostMapping("/emi")
    public LoanDetailsResponseModel calculateEmi(@RequestBody @Valid LoanDetailsRequestModel loanDetailsRequestModel) {
        double monthlyInstallment = loanDetailsService.calculateEmi(loanDetailsRequestModel);
        return LoanDetailsResponseModel.builder()
                .monthlyInstallment(monthlyInstallment)
                .build();
    }
}
