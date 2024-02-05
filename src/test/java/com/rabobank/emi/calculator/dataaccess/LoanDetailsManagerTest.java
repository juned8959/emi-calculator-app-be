package com.rabobank.emi.calculator.dataaccess;

import com.rabobank.emi.calculator.entity.LoanDetails;
import com.rabobank.emi.calculator.exception.EmiCalculatorException;
import com.rabobank.emi.calculator.repository.LoanDetailsRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class LoanDetailsManagerTest {

    @Autowired
    private LoanDetailsRepository loanDetailsRepository;
    private LoanDetailsManager sut;

    @BeforeEach
    void setup() {
        sut = new LoanDetailsManager(loanDetailsRepository);
    }

    @Test
    @Transactional
    void shouldSaveLoanDetails() {
        final LoanDetails loanDetails = LoanDetails.builder()
                .principal(1000000)
                .yearlyRateOfInterest(7.2)
                .tenureInYears(10)
                .monthlyInstallment(11714.19)
                .emailId("example@example.com")
                .build();

        sut.saveLoanDetails(loanDetails);

        final List<LoanDetails> savedLoanDetailsList = loanDetailsRepository.findAll();
        final LoanDetails savedLoanDetails = savedLoanDetailsList.getFirst();
        assertEquals(1, savedLoanDetailsList.size());
        assertNotNull(savedLoanDetails.getId());
        assertEquals(loanDetails.getPrincipal(), savedLoanDetails.getPrincipal());
        assertEquals(loanDetails.getYearlyRateOfInterest(), savedLoanDetails.getYearlyRateOfInterest());
        assertEquals(loanDetails.getTenureInYears(), savedLoanDetails.getTenureInYears());
        assertEquals(loanDetails.getMonthlyInstallment(), savedLoanDetails.getMonthlyInstallment());
        assertEquals(loanDetails.getEmailId(), savedLoanDetails.getEmailId());
    }

    @Test
    @Transactional
    public void shouldThrowEmiCalculatorExceptionIfLoanDetailsCouldNotBeSaved() {
        final LoanDetails loanDetails = LoanDetails.builder()
                .id(1L)
                .principal(1000000)
                .yearlyRateOfInterest(7.2)
                .tenureInYears(10)
                .monthlyInstallment(11714.19)
                .build();

        // Database should throw constraint violation exception as emailId is null.
        Assertions.assertThrows(EmiCalculatorException.class, () -> {
            sut.saveLoanDetails(loanDetails);
        });
    }
}