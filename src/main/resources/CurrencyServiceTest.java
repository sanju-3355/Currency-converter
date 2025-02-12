package com.example.currencyconversion.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getExchangeRatesTest() {
        // Mock the external API response
        when(restTemplate.getForObject("https://api.exchangeratesapi.io/latest?base=USD&access_key=Yff902f08131b4fbb824b89b098d9dc99", Map.class))
                .thenReturn(Map.of("rates", Map.of("EUR", 0.94, "GBP", 0.75)));

        Map<String, Double> rates = currencyService.getExchangeRates("USD");

        assertEquals(0.94, rates.get("EUR"));
    }

    @Test
    void convertCurrencyTest() {
        when(restTemplate.getForObject("https://api.exchangeratesapi.io/latest?base=USD&access_key=Yff902f08131b4fbb824b89b098d9dc99", Map.class))
                .thenReturn(Map.of("rates", Map.of("EUR", 0.94)));

        double result = currencyService.convertCurrency(100, "USD", "EUR");

        assertEquals(94.0, result);
    }
}
