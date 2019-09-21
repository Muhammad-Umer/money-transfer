package com.revolut.moneytransfer.service.impl;

import com.revolut.moneytransfer.service.ForexService;
import com.revolut.moneytransfer.type.application.Currency;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class ForexServiceImpl implements ForexService {

    @Override
    public BigDecimal convertCurrencyToDollars(Currency currency, BigDecimal amount) {

        switch (currency) {
            case PKR:
                return amount.multiply(BigDecimal.valueOf(0.0064));
            case GBP:
                return amount.multiply(BigDecimal.valueOf(1.25));
            case EUR:
                return amount.multiply(BigDecimal.valueOf(1.10));
            case INR:
                return amount.multiply(BigDecimal.valueOf(156.70));
            case YEN:
                return amount.multiply(BigDecimal.valueOf(0.014));
            case AED:
                return amount.multiply(BigDecimal.valueOf(0.27));
            default:
                return amount;
        }
    }

    @Override
    public BigDecimal convertDollarsToCurrency(Currency currency, BigDecimal amount) {

        switch (currency) {
            case PKR:
                return amount.multiply(BigDecimal.valueOf(156.70));
            case GBP:
                return amount.multiply(BigDecimal.valueOf(0.80));
            case EUR:
                return amount.multiply(BigDecimal.valueOf(0.91));
            case INR:
                return amount.multiply(BigDecimal.valueOf(71.39));
            case YEN:
                return amount.multiply(BigDecimal.valueOf(108.03));
            case AED:
                return amount.multiply(BigDecimal.valueOf(3.67));
            default:
                return amount;
        }
    }

}
