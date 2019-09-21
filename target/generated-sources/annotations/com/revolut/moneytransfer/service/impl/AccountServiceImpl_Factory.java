package com.revolut.moneytransfer.service.impl;

import com.revolut.moneytransfer.repository.AccountRepository;
import com.revolut.moneytransfer.repository.TransactionRepository;
import com.revolut.moneytransfer.repository.UserRepository;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AccountServiceImpl_Factory implements Factory<AccountServiceImpl> {
  private final Provider<AccountRepository> accountRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public AccountServiceImpl_Factory(
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.accountRepositoryProvider = accountRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public AccountServiceImpl get() {
    return provideInstance(
        accountRepositoryProvider, userRepositoryProvider, transactionRepositoryProvider);
  }

  public static AccountServiceImpl provideInstance(
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new AccountServiceImpl(
        accountRepositoryProvider.get(),
        userRepositoryProvider.get(),
        transactionRepositoryProvider.get());
  }

  public static AccountServiceImpl_Factory create(
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new AccountServiceImpl_Factory(
        accountRepositoryProvider, userRepositoryProvider, transactionRepositoryProvider);
  }

  public static AccountServiceImpl newAccountServiceImpl(
      AccountRepository accountRepository,
      UserRepository userRepository,
      TransactionRepository transactionRepository) {
    return new AccountServiceImpl(accountRepository, userRepository, transactionRepository);
  }
}
