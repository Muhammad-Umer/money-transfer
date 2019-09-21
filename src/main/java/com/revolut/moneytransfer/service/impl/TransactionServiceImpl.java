package com.revolut.moneytransfer.service.impl;

import com.neovisionaries.i18n.CountryCode;
import com.revolut.moneytransfer.dto.Account;
import com.revolut.moneytransfer.dto.Transaction;
import com.revolut.moneytransfer.dto.User;
import com.revolut.moneytransfer.exception.ServiceException;
import com.revolut.moneytransfer.repository.AccountRepository;
import com.revolut.moneytransfer.repository.TransactionRepository;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.service.ForexService;
import com.revolut.moneytransfer.service.TransactionService;
import com.revolut.moneytransfer.type.application.Currency;
import com.revolut.moneytransfer.type.error.AccountErrorType;
import com.revolut.moneytransfer.type.error.TransactionErrorType;
import com.revolut.moneytransfer.type.error.UserErrorType;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final ForexService forexService;

    @Inject
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  UserRepository userRepository,
                                  AccountRepository accountRepository,
                                  ForexService forexService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.forexService = forexService;
    }

    @Override
    public Transaction add(Transaction transaction) {
        if (transaction.getFromAccount() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT_ID);
        }

        Account senderAccount = accountRepository.findById(transaction.getFromAccount());
        if (senderAccount.getId() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT);
        }

        if (transaction.getToAccount() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT_ID);
        }

        Account recipientAccount = accountRepository.findById(transaction.getToAccount());
        if (recipientAccount.getId() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT);
        }

        User sender = userRepository.findById(transaction.getFromAccount());
        if (sender.getId() == null) {
            throw new ServiceException(UserErrorType.INVALID_USER);
        }

        User receiver = userRepository.findById(transaction.getToAccount());
        if (receiver.getId() == null) {
            throw new ServiceException(UserErrorType.INVALID_USER);
        }

        CountryCode senderCountryCode = CountryCode.getByAlpha3Code(sender.getCountry());
        CountryCode receiverCountryCode = CountryCode.getByAlpha3Code(receiver.getCountry());

        BigDecimal dollarAmount = forexService
                .convertCurrencyToDollars(
                        Currency.valueOf(senderCountryCode.getCurrency().getCurrencyCode()),
                        transaction.getAmount());

        BigDecimal localCurrencyAmount = forexService
                .convertCurrencyToDollars(
                        Currency.valueOf(receiverCountryCode.getCurrency().getCurrencyCode()),
                        dollarAmount);

        recipientAccount.setAccountBalance(recipientAccount.getAccountBalance().add(localCurrencyAmount));
        senderAccount.setAccountBalance(senderAccount.getAccountBalance().subtract(transaction.getAmount()));

        transaction.setAmount(localCurrencyAmount);
        transaction.setCreationDate(new Timestamp(System.currentTimeMillis()));
        transaction.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        Transaction actualTransaction = transactionRepository.add(transaction);
        accountRepository.update(recipientAccount);
        accountRepository.update(senderAccount);

        return actualTransaction;
    }

    @Override
    public Transaction update(Transaction transaction) {
        if (transaction.getId() == null) {
            throw new ServiceException(TransactionErrorType.INVALID_TRANSACTION_ID);
        }

        if (transaction.getFromAccount() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT_ID);
        }

        Account senderAccount = accountRepository.findById(transaction.getFromAccount());
        if (senderAccount.getId() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT);
        }

        if (transaction.getToAccount() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT_ID);
        }

        Account recipientAccount = accountRepository.findById(transaction.getToAccount());
        if (recipientAccount.getId() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT);
        }

        User sender = userRepository.findById(transaction.getFromAccount());
        if (sender.getId() == null) {
            throw new ServiceException(UserErrorType.INVALID_USER);
        }

        User receiver = userRepository.findById(transaction.getToAccount());
        if (receiver.getId() == null) {
            throw new ServiceException(UserErrorType.INVALID_USER);
        }

        CountryCode senderCountryCode = CountryCode.getByAlpha3Code(sender.getCountry());
        CountryCode receiverCountryCode = CountryCode.getByAlpha3Code(receiver.getCountry());

        BigDecimal dollarAmount = forexService
                .convertCurrencyToDollars(
                        Currency.valueOf(senderCountryCode.getCurrency().getCurrencyCode()),
                        transaction.getAmount());

        BigDecimal localCurrencyAmount = forexService
                .convertCurrencyToDollars(
                        Currency.valueOf(receiverCountryCode.getCurrency().getCurrencyCode()),
                        dollarAmount);

        recipientAccount.setAccountBalance(recipientAccount.getAccountBalance().add(localCurrencyAmount));
        senderAccount.setAccountBalance(senderAccount.getAccountBalance().subtract(transaction.getAmount()));

        transaction.setAmount(localCurrencyAmount);
        transaction.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        Transaction actualTransaction = transactionRepository.update(transaction);
        accountRepository.update(recipientAccount);
        accountRepository.update(senderAccount);

        return actualTransaction;
    }

    @Override
    public Transaction findById(String transactionId) {
        return transactionRepository.findById(Integer.valueOf(transactionId));
    }

    @Override
    public List<Transaction> getDebitTransactions(String accountId) {
        Account account = accountRepository.findById(Integer.valueOf(accountId));
        if (account.getId() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT);
        }

        return transactionRepository.getDebitTransactions(Integer.valueOf(accountId));
    }

    @Override
    public List<Transaction> getCreditTransactions(String accountId) {
        Account account = accountRepository.findById(Integer.valueOf(accountId));
        if (account.getId() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT);
        }

        return transactionRepository.getCreditTransactions(Integer.valueOf(accountId));
    }
}
