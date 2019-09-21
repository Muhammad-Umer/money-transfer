package com.revolut.moneytransfer.service.impl;

import com.revolut.moneytransfer.exception.ServiceException;
import com.revolut.moneytransfer.dto.Account;
import com.revolut.moneytransfer.dto.Transaction;
import com.revolut.moneytransfer.dto.User;
import com.revolut.moneytransfer.repository.AccountRepository;
import com.revolut.moneytransfer.repository.TransactionRepository;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.service.AccountService;
import com.revolut.moneytransfer.type.application.AccountType;
import com.revolut.moneytransfer.type.error.AccountErrorType;
import com.revolut.moneytransfer.type.error.UserErrorType;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository,
                              UserRepository userRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Account add(Account account) {
        if (account.getUserId() == null) {
            throw new ServiceException(UserErrorType.INVALID_USER_ID);
        }

        if (AccountType.getAccountType(account.getAccountType()) == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT_TYPE);
        }

        User user = userRepository.findById(account.getUserId());
        if (user.getId() == null) {
            throw new ServiceException(UserErrorType.INVALID_USER);
        }

        account.setCreationDate(new Timestamp(System.currentTimeMillis()));
        account.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        return accountRepository.add(account);
    }

    @Override
    public Account update(Account account) {
        if (account.getId() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT_ID);
        }

        if (account.getUserId() == null) {
            throw new ServiceException(UserErrorType.INVALID_USER_ID);
        }

        if (account.getAccountType() == null ||
                AccountType.getAccountType(account.getAccountType()) == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT_TYPE);
        }

        User user = userRepository.findById(account.getUserId());
        if (user.getId() == null) {
            throw new ServiceException(UserErrorType.INVALID_USER);
        }

        account.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        return accountRepository.update(account);
    }

    @Override
    public boolean close(String accountId) {
        return accountRepository.delete(Integer.valueOf(accountId));
    }

    @Override
    public Account findById(String accountId) {
        return accountRepository.findById(Integer.valueOf(accountId));
    }

    @Override
    public List<Account> findByUser(String userId) {
        return accountRepository.findByUser(Integer.valueOf(userId));
    }

    @Override
    public List<Account> findByUserAndAccountType(String userId, String accountType) {
        if (AccountType.getAccountType(Integer.valueOf(accountType)) == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT_TYPE);
        }

        return accountRepository.
                findByUserAndAccountType(Integer.valueOf(userId), Integer.valueOf(accountType));
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.getAll();
    }

    @Override
    public Transaction transfer(String senderId, String recipientId, String amount) {
        Account senderAccount = findById(senderId);
        if(senderAccount.getId() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT);
        }

        Account recipientAccount = findById(recipientId);
        if(recipientAccount.getId() == null) {
            throw new ServiceException(AccountErrorType.INVALID_ACCOUNT);
        }

        Transaction transaction = Transaction.builder()
                .fromAccount(Integer.valueOf(senderId))
                .toAccount(Integer.valueOf(recipientId))
                .amount(new BigDecimal(amount))
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .updateDate(new Timestamp(System.currentTimeMillis()))
                .build();

        final BigDecimal remainingSenderBalance = senderAccount.getAccountBalance().subtract(new BigDecimal(amount));

        if (remainingSenderBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException(AccountErrorType.INSUFFICIENT_BALANCE);
        }

        senderAccount.setAccountBalance(remainingSenderBalance);
        recipientAccount.setAccountBalance(recipientAccount.getAccountBalance().add(new BigDecimal(amount)));

        Transaction actualTransaction = transactionRepository.add(transaction);
        update(senderAccount);
        update(recipientAccount);

        return actualTransaction;
    }
}
