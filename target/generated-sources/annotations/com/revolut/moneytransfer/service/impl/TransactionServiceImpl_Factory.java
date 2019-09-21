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

  private final Provider<ForexService> forexServiceProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  public TransactionServiceImpl_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<ForexService> forexServiceProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.forexServiceProvider = forexServiceProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
  }

  @Override
  public TransactionServiceImpl get() {
    return provideInstance(
        transactionRepositoryProvider,
        userRepositoryProvider,
        forexServiceProvider,
        accountRepositoryProvider);
  }

  public static TransactionServiceImpl provideInstance(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<ForexService> forexServiceProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    return new TransactionServiceImpl(
        transactionRepositoryProvider.get(),
        userRepositoryProvider.get(),
        forexServiceProvider.get(),
        accountRepositoryProvider.get());
  }

  public static TransactionServiceImpl_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<ForexService> forexServiceProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    return new TransactionServiceImpl_Factory(
        transactionRepositoryProvider,
        userRepositoryProvider,
        forexServiceProvider,
        accountRepositoryProvider);
  }

  public static TransactionServiceImpl newTransactionServiceImpl(
      TransactionRepository transactionRepository,
      UserRepository userRepository,
      ForexService forexService,
      AccountRepository accountRepository) {
    return new TransactionServiceImpl(
        transactionRepository, userRepository, forexService, accountRepository);
  }
}
