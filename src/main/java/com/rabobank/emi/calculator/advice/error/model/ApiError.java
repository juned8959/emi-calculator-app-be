package com.rabobank.emi.calculator.advice.error.model;

import lombok.Builder;

@Builder
public record ApiError(String title, int statusCode, String details) {
}
