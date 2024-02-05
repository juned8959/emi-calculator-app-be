package com.rabobank.emi.calculator.model.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanDetailsRequestModel {
    @Positive(message = "principal must be a positive number")
    private double principal;

    @Positive(message = "yearlyRateOfInterest must be a positive number")
    @Max(value = 100, message = "yearlyRateOfInterest must be between 0 to 100")
    private double yearlyRateOfInterest;

    @Positive(message = "tenureInYears must be a positive number")
    @Max(value = 30, message = "tenureInYears must not exceed 30")
    private int tenureInYears;

    @NotNull(message = "email is mandatory")
    @Email(message = "please enter a valid email address")
    private String email;
}