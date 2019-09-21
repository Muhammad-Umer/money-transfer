package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.repository.AccountRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TransactionModule_ProvideAccountRepositoryFactory
    implements Factory<AccountRepository> {
  private final TransactionModule module;

  public TransactionModule_ProvideAccountRepositoryFactory(TransactionModule module) {
    this.module = module;
  }

  @Override
  public AccountRepository get() {
    return provideInstance(module);
  }

  public static AccountRepository provideInstance(TransactionModule module) {
    return proxyProvideAccountRepository(module);
  }

  public static TransactionModule_ProvideAccountRepositoryFactory create(TransactionModule module) {
    return new TransactionModule_ProvideAccountRepositoryFactory(module);
  }

  public static AccountRepository proxyProvideAccountRepository(TransactionModule instance) {
    return Preconditions.checkNotNull(
        instance.provideAccountRepository(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
