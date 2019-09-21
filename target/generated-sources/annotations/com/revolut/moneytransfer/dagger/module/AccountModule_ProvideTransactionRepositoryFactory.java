package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.repository.TransactionRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AccountModule_ProvideTransactionRepositoryFactory
    implements Factory<TransactionRepository> {
  private final AccountModule module;

  public AccountModule_ProvideTransactionRepositoryFactory(AccountModule module) {
    this.module = module;
  }

  @Override
  public TransactionRepository get() {
    return provideInstance(module);
  }

  public static TransactionRepository provideInstance(AccountModule module) {
    return proxyProvideTransactionRepository(module);
  }

  public static AccountModule_ProvideTransactionRepositoryFactory create(AccountModule module) {
    return new AccountModule_ProvideTransactionRepositoryFactory(module);
  }

  public static TransactionRepository proxyProvideTransactionRepository(AccountModule instance) {
    return Preconditions.checkNotNull(
        instance.provideTransactionRepository(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
