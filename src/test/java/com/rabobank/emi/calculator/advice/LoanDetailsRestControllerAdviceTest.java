package com.rabobank.emi.calculator.advice;

import com.rabobank.emi.calculator.advice.error.model.ApiError;
import com.rabobank.emi.calculator.exception.EmiCalculatorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoanDetailsRestControllerAdviceTest {

    private final LoanDetailsRestControllerAdvice sut = new LoanDetailsRestControllerAdvice();

    @Test
    void testHandleEmiCalculatorException() {
        final String message = "Unable to calculate emi";
        final EmiCalculatorException exception = new EmiCalculatorException(message, new RuntimeException());

        final ApiError apiError = sut.handleEmiCalculatorException(exception);

        assertEquals("Internal Server Error", apiError.title());
        assertEquals(500, apiError.statusCode());
        assertEquals(message, apiError.details());
    }
}