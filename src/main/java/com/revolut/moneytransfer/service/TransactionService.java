package com.revolut.moneytransfer.service;

import com.revolut.moneytransfer.dto.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction add(Transaction transaction);

    Transaction update(Transaction transaction);

    Transaction findById(String transactionId);

    List<Transaction> getDebitTransactions(String accountId);

    List<Transaction> getCreditTransactions(String accountId);
}
