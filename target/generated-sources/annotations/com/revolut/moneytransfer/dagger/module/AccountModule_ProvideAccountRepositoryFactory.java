package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.repository.AccountRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AccountModule_ProvideAccountRepositoryFactory
    implements Factory<AccountRepository> {
  private final AccountModule module;

  public AccountModule_ProvideAccountRepositoryFactory(AccountModule module) {
    this.module = module;
  }

  @Override
  public AccountRepository get() {
    return provideInstance(module);
  }

  public static AccountRepository provideInstance(AccountModule module) {
    return proxyProvideAccountRepository(module);
  }

  public static AccountModule_ProvideAccountRepositoryFactory create(AccountModule module) {
    return new AccountModule_ProvideAccountRepositoryFactory(module);
  }

  public static AccountRepository proxyProvideAccountRepository(AccountModule instance) {
    return Preconditions.checkNotNull(
        instance.provideAccountRepository(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
