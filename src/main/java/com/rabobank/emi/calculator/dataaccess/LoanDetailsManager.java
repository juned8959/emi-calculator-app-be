package com.rabobank.emi.calculator.dataaccess;

import com.rabobank.emi.calculator.entity.LoanDetails;
import com.rabobank.emi.calculator.exception.EmiCalculatorException;
import com.rabobank.emi.calculator.repository.LoanDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The LoanDetailsManager class manages the persistence of loan details.
 * It provides functionality to save loan details to the database using a repository.
 * This class is responsible for handling exceptions that may occur during the saving process
 * and logging relevant information.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoanDetailsManager {

    private final LoanDetailsRepository loanDetailsRepository;

    /**
     * Saves the provided loan details to the database.
     *
     * @param loanDetails The loan details to be saved.
     * @throws EmiCalculatorException if there is an issue while saving the loan details.
     */
    public void saveLoanDetails(final LoanDetails loanDetails) {
        try {
            loanDetailsRepository.save(loanDetails);
        } catch (Exception ex) {
            log.error(String.format("Unable to Save Loan Details {%s}", ex.getMessage()));
            throw new EmiCalculatorException("Unable to Save Loan Details", ex);
        }
    }
}
