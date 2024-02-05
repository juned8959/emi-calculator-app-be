package com.rabobank.emi.calculator.advice;

import com.rabobank.emi.calculator.advice.error.model.ApiError;
import com.rabobank.emi.calculator.exception.EmiCalculatorException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * This class handles exceptions such as MethodArgumentNotValidException and EmiCalculatorException,
 * and converts them into appropriate API error responses.
 */
@RestControllerAdvice
public class LoanDetailsRestControllerAdvice {

    /**
     * Handles MethodArgumentNotValidException and returns a BAD_REQUEST response
     * with details of validation errors.
     *
     * @param ex The MethodArgumentNotValidException to be handled.
     * @return ApiError object containing details of the bad request.
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        List<String> errorDetails = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ApiError.builder()
                .title("Bad Request")
                .statusCode(BAD_REQUEST.value())
                .details(String.join(",", errorDetails))
                .build();
    }

    /**
     * Handles EmiCalculatorException and returns an INTERNAL_SERVER_ERROR response
     * with details of the exception.
     *
     * @param ex The EmiCalculatorException to be handled.
     * @return An ApiError object containing details of the error.
     */
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmiCalculatorException.class)
    public ApiError handleEmiCalculatorException(final EmiCalculatorException ex) {
        return ApiError.builder()
                .title("Internal Server Error")
                .statusCode(INTERNAL_SERVER_ERROR.value())
                .details(ex.getMessage())
                .build();
    }
}
