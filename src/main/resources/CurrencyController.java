package com.example.currencyconversion.controller;

import com.example.currencyconversion.service.CurrencyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Value("${default.base.currency}")
    private String defaultBaseCurrency;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    // Endpoint to fetch exchange rates for a given base currency
    @GetMapping("/rates")
    public ResponseEntity<Map<String, Double>> getExchangeRates(@RequestParam(value = "base", defaultValue = "USD") String base) {
        try {
            Map<String, Double> exchangeRates = currencyService.getExchangeRates(base);
            return new ResponseEntity<>(exchangeRates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to convert an amount from one currency to another
    @PostMapping("/convert")
    public ResponseEntity<Map<String, Object>> convertCurrency(@RequestBody Map<String, Object> conversionRequest) {
        try {
            String fromCurrency = (String) conversionRequest.get("from");
            String toCurrency = (String) conversionRequest.get("to");
            double amount = (double) conversionRequest.get("amount");

            double convertedAmount = currencyService.convertCurrency(amount, fromCurrency, toCurrency);

            return ResponseEntity.ok(Map.of(
                    "from", fromCurrency,
                    "to", toCurrency,
                    "amount", amount,
                    "convertedAmount", convertedAmount
            ));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
