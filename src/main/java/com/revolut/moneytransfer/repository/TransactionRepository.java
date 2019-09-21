package com.revolut.moneytransfer.repository;

import com.revolut.moneytransfer.dto.Transaction;

import java.util.List;

public interface TransactionRepository extends GenericRepository<Transaction>  {

    List<Transaction> getDebitTransactions(Integer accountId);

    List<Transaction> getCreditTransactions(Integer accountId);
}
