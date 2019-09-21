package com.revolut.moneytransfer.service;

import com.revolut.moneytransfer.dto.Account;
import com.revolut.moneytransfer.dto.Transaction;

import java.util.List;

public interface AccountService {
    Account add(Account account);

    Account update(Account account);

    boolean close(String accountId);

    Account findById(String accountId);

    List<Account> findByUser(String userId);

    List<Account> findByUserAndAccountType(String userId, String accountType);

    List<Account> getAll();

    Transaction transfer(String senderId, String recipientId, String amount);

    Transaction deposit(String accountId, String amount);

    Transaction withdraw(String accountId, String amount);
}
