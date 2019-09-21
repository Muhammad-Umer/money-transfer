package com.revolut.moneytransfer.service;

import com.revolut.moneytransfer.type.application.Currency;

import java.math.BigDecimal;

public interface ForexService {
    BigDecimal convertCurrencyToDollars(Currency currency, BigDecimal amount);

    BigDecimal convertDollarsToCurrency(Currency currency, BigDecimal amount);
}
