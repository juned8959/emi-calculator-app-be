package com.rabobank.emi.calculator.exception;

public class EmiCalculatorException extends RuntimeException{
    public EmiCalculatorException(final String message, final Throwable ex){
        super(message, ex);
    }
}
