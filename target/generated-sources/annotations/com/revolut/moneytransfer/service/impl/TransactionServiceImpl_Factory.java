package com.revolut.moneytransfer.service.impl;

import com.revolut.moneytransfer.repository.AccountRepository;
import com.revolut.moneytransfer.repository.TransactionRepository;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.service.ForexService;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TransactionServiceImpl_Factory implements Factory<TransactionServiceImpl> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  private final Provider<ForexService> forexServiceProvider;

  public TransactionServiceImpl_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<ForexService> forexServiceProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
    this.forexServiceProvider = forexServiceProvider;
  }

  @Override
  public TransactionServiceImpl get() {
    return provideInstance(
        transactionRepositoryProvider,
        userRepositoryProvider,
        accountRepositoryProvider,
        forexServiceProvider);
  }

  public static TransactionServiceImpl provideInstance(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<ForexService> forexServiceProvider) {
    return new TransactionServiceImpl(
        transactionRepositoryProvider.get(),
        userRepositoryProvider.get(),
        accountRepositoryProvider.get(),
        forexServiceProvider.get());
  }

  public static TransactionServiceImpl_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<ForexService> forexServiceProvider) {
    return new TransactionServiceImpl_Factory(
        transactionRepositoryProvider,
        userRepositoryProvider,
        accountRepositoryProvider,
        forexServiceProvider);
  }

  public static TransactionServiceImpl newTransactionServiceImpl(
      TransactionRepository transactionRepository,
      UserRepository userRepository,
      AccountRepository accountRepository,
      ForexService forexService) {
    return new TransactionServiceImpl(
        transactionRepository, userRepository, accountRepository, forexService);
  }
}
