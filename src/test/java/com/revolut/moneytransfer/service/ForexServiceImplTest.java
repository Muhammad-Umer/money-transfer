package com.revolut.moneytransfer.service;

import com.revolut.moneytransfer.service.impl.ForexServiceImpl;
import com.revolut.moneytransfer.type.application.Currency;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ForexServiceImplTest {

    private ForexService forexService = new ForexServiceImpl();

    @Test
    @DisplayName("Test PKR use-case")
    public void convertToLocalCurrencyPKR () {
        BigDecimal amount = forexService.convertDollarsToCurrency(Currency.PKR, BigDecimal.ONE);
        assertEquals(amount, BigDecimal.valueOf(156.70));
    }

    @Test
    @DisplayName("Test GBP use-case")
    public void convertToLocalCurrencyGBP () {
        BigDecimal amount = forexService.convertDollarsToCurrency(Currency.GBP, BigDecimal.ONE);
        assertEquals(amount, BigDecimal.valueOf(0.80));
    }

    @Test
    @DisplayName("Test EUR use-case")
    public void convertToLocalCurrencyEUR () {
        BigDecimal amount = forexService.convertDollarsToCurrency(Currency.EUR, BigDecimal.ONE);
        assertEquals(amount, BigDecimal.valueOf(0.91));
    }

    @Test
    @DisplayName("Test INR use-case")
    public void convertToLocalCurrencyINR () {
        BigDecimal amount = forexService.convertDollarsToCurrency(Currency.INR, BigDecimal.ONE);
        assertEquals(amount, BigDecimal.valueOf(71.39));
    }

    @Test
    @DisplayName("Test YEN use-case")
    public void convertToLocalCurrencyYEN () {
        BigDecimal amount = forexService.convertDollarsToCurrency(Currency.YEN, BigDecimal.ONE);
        assertEquals(amount, BigDecimal.valueOf(108.03));
    }

    @Test
    @DisplayName("Test AED use-case")
    public void convertToLocalCurrencyAED () {
        BigDecimal amount = forexService.convertDollarsToCurrency(Currency.AED, BigDecimal.ONE);
        assertEquals(amount, BigDecimal.valueOf(3.67));
    }

    @Test
    @DisplayName("Test default use-case")
    public void convertToLocalCurrencyDefault () {
        BigDecimal amount = forexService.convertDollarsToCurrency(Currency.USD, BigDecimal.ONE);
        assertEquals(amount, BigDecimal.ONE);
    }

    @Test
    @DisplayName("Test PKR use-case")
    public void convertCurrencyToDollarsPKR () {
        BigDecimal amount = forexService.convertCurrencyToDollars(Currency.PKR, BigDecimal.valueOf(156.70));
        assertEquals(amount.setScale(0, RoundingMode.HALF_UP), BigDecimal.ONE);
    }

    @Test
    @DisplayName("Test GBP use-case")
    public void convertCurrencyToDollarsGBP () {
        BigDecimal amount = forexService.convertCurrencyToDollars(Currency.GBP, BigDecimal.valueOf(0.80));
        assertEquals(amount.setScale(0, RoundingMode.HALF_UP), BigDecimal.ONE);
    }

    @Test
    @DisplayName("Test EUR use-case")
    public void convertCurrencyToDollarsEUR () {
        BigDecimal amount = forexService.convertCurrencyToDollars(Currency.EUR, BigDecimal.valueOf(0.91));
        assertEquals(amount.setScale(0, RoundingMode.HALF_UP), BigDecimal.ONE);
    }

    @Test
    @DisplayName("Test INR use-case")
    public void convertCurrencyToDollarsINR () {
        BigDecimal amount = forexService.convertCurrencyToDollars(Currency.INR, BigDecimal.valueOf(71.39));
        assertEquals(amount.setScale(0, RoundingMode.HALF_UP), BigDecimal.ONE);
    }

    @Test
    @DisplayName("Test YEN use-case")
    public void convertCurrencyToDollarsYEN () {
        BigDecimal amount = forexService.convertCurrencyToDollars(Currency.YEN, BigDecimal.valueOf(108.03));
        assertEquals(amount.setScale(0, RoundingMode.HALF_UP), BigDecimal.ONE);
    }

    @Test
    @DisplayName("Test AED use-case")
    public void convertCurrencyToDollarsAED () {
        BigDecimal amount = forexService.convertCurrencyToDollars(Currency.AED, BigDecimal.valueOf(3.67));
        assertEquals(amount.setScale(0, RoundingMode.HALF_UP), BigDecimal.ONE);
    }

    @Test
    @DisplayName("Test default use-case")
    public void convertCurrencyToDollarsDefault () {
        BigDecimal amount = forexService.convertCurrencyToDollars(Currency.USD, BigDecimal.ONE);
        assertEquals(amount, BigDecimal.ONE);
    }
}
