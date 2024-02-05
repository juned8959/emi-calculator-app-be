package com.rabobank.emi.calculator.model.response;

import lombok.Builder;

@Builder
public record LoanDetailsResponseModel(double monthlyInstallment) {
}