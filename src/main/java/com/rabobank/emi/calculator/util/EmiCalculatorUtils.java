package com.rabobank.emi.calculator.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class EmiCalculatorUtils {
    /**
     * Computes the Equated Monthly Installment (EMI) based on the principal amount,
     * rate of interest, and tenure.
     *
     * @param principal The principal loan amount.
     * @param rate      The rate of interest (in percentage) per year.
     * @param tenure    The tenure of the loan (in years).
     * @return The calculated EMI amount.
     */
    public static double computeEmi(double principal, double rate, int tenure) {
        double monthlyRate = rate / (12 * 100);
        int tenureInMonths = tenure * 12;
        double interestComponentConstant = (Math.pow(1 + monthlyRate, tenureInMonths));
        return formatEmi((principal * monthlyRate * interestComponentConstant) / (interestComponentConstant - 1));
    }

    /**
     * Formats the EMI value to two decimal places.
     *
     * @param emi The EMI amount to be formatted.
     * @return The formatted EMI amount.
     */
    private static double formatEmi(double emi) {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
        DecimalFormat df = new DecimalFormat("#.##", symbols);
        return Double.parseDouble(df.format(emi));
    }
}