package com.rabobank.emi.calculator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.emi.calculator.advice.error.model.ApiError;
import com.rabobank.emi.calculator.model.request.LoanDetailsRequestModel;
import com.rabobank.emi.calculator.model.response.LoanDetailsResponseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class EmiCalculatorIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String CALCULATE_EMI_POST_URL = "/api/calculate/emi";

    @Test
    void shouldCalculateEmi() throws Exception {
        final LoanDetailsRequestModel request = LoanDetailsRequestModel.builder()
                .principal(1000000)
                .tenureInYears(10)
                .yearlyRateOfInterest(7.2)
                .email("example@example.com")
                .build();

        final String requestBody = convertToJson(request);
        final MvcResult result = performHttpRequest(requestBody)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final LoanDetailsResponseModel response = getResponse(result, LoanDetailsResponseModel.class);
        assertEquals(11714.19, response.monthlyInstallment());
    }

    @ParameterizedTest
    @MethodSource("loanDetailsProvider")
    void shouldThrowBadRequestForInvalidInputs(final LoanDetailsRequestModel request, final String expectedMessage) throws Exception {
        final String requestBody = convertToJson(request);

        final MvcResult result = performHttpRequest(requestBody)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        final ApiError response = getResponse(result, ApiError.class);
        assertEquals("Bad Request", response.title());
        assertEquals(400, response.statusCode());
        assertEquals(expectedMessage, response.details());
    }

    private static Stream<Arguments> loanDetailsProvider() {
        return Stream.of(
                Arguments.of(
                        LoanDetailsRequestModel.builder()
                                .principal(-1000000)
                                .tenureInYears(10)
                                .yearlyRateOfInterest(7.2)
                                .email("example@example.com")
                                .build(),
                        "principal must be a positive number"
                ),
                Arguments.of(
                        LoanDetailsRequestModel.builder()
                                .principal(1000000)
                                .tenureInYears(10)
                                .yearlyRateOfInterest(-7.2)
                                .email("example@example.com")
                                .build(),
                        "yearlyRateOfInterest must be a positive number"
                ),
                Arguments.of(
                        LoanDetailsRequestModel.builder()
                                .principal(1000000)
                                .tenureInYears(10)
                                .yearlyRateOfInterest(720)
                                .email("example@example.com")
                                .build(),
                        "yearlyRateOfInterest must be between 0 to 100"
                ),
                Arguments.of(
                        LoanDetailsRequestModel.builder()
                                .principal(1000000)
                                .tenureInYears(0)
                                .yearlyRateOfInterest(7.2)
                                .email("example@example.com")
                                .build(),
                        "tenureInYears must be a positive number"
                ),
                Arguments.of(
                        LoanDetailsRequestModel.builder()
                                .principal(1000000)
                                .tenureInYears(50)
                                .yearlyRateOfInterest(7.2)
                                .email("example@example.com")
                                .build(),
                        "tenureInYears must not exceed 30"
                ),
                Arguments.of(
                        LoanDetailsRequestModel.builder()
                                .principal(1000000)
                                .tenureInYears(10)
                                .yearlyRateOfInterest(7.2)
                                .build(),
                        "email is mandatory"
                ),
                Arguments.of(
                        LoanDetailsRequestModel.builder()
                                .principal(1000000)
                                .tenureInYears(10)
                                .yearlyRateOfInterest(7.2)
                                .email("example.com")
                                .build(),
                        "please enter a valid email address"
                )
        );
    }

    private <T> T getResponse(MvcResult result, Class<T> classType) throws UnsupportedEncodingException, JsonProcessingException {
        final String responseBody = result.getResponse().getContentAsString();
        return objectMapper.readValue(responseBody, classType);
    }

    private ResultActions performHttpRequest(String requestBody) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(CALCULATE_EMI_POST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
    }

    private String convertToJson(final LoanDetailsRequestModel request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}