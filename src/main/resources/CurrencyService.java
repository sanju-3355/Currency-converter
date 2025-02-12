package com.example.currencyconversion.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class CurrencyService {

    @Value("${exchange.api.url}")
    private String apiUrl;

    @Value("${exchange.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch exchange rates from external API
    public Map<String, Double> getExchangeRates(String baseCurrency) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("base", baseCurrency)
                .queryParam("access_key", apiKey)
                .toUriString();

        // Handle the external API response
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("rates")) {
                return (Map<String, Double>) response.get("rates");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching exchange rates from external API.", e);
        }
        throw new RuntimeException("Invalid response from exchange rates API.");
    }

    // Convert the amount from one currency to another
    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        Map<String, Double> exchangeRates = getExchangeRates(fromCurrency);
        Double rate = exchangeRates.get(toCurrency);

        if (rate == null) {
            throw new IllegalArgumentException("Invalid target currency: " + toCurrency);
        }
        return amount * rate;
    }
}
